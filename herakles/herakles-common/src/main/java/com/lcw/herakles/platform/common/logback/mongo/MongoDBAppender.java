package com.lcw.herakles.platform.common.logback.mongo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;
import org.slf4j.Marker;

import com.lcw.herakles.platform.common.util.DateUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

/**
 * logback appender that uses Mongo to log messages.
 * 
 * @author chenwulou
 */
public class MongoDBAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private String host;
    private int port;
    private String db;
    private Map<String, MongoCollection<Document>> logCollectionMap =
            new HashMap<String, MongoCollection<Document>>();
    private MongoDatabase mongoDb = null;

    public MongoDBAppender() {}

    public MongoCollection<Document> getLogCollection(String level) {
        MongoCollection<Document> logCollection = logCollectionMap.get(level);
        if (logCollection == null) {
            logCollection = mongoDb.getCollection("log_" + level.toLowerCase() + "_events");
            logCollectionMap.put(level, logCollection);
        }
        return logCollection;
    }

    @Override
    public void start() {
        try {
            connect();
            super.start();
        } catch (UnknownHostException | MongoException e) {
            addError("Can't connect to mongo: host=" + host + ", port=" + port, e);
        }
    }

    @SuppressWarnings("resource")
    private void connect() throws UnknownHostException {
        MongoClient client = new MongoClient(host, port);
        mongoDb = client.getDatabase(db == null ? "herakles" : db);
    }

    @Override
    protected void append(ILoggingEvent evt) {
        if (evt == null)
            return; // just in case
        String level = String.valueOf(evt.getLevel());
        Document log = getBasicLog(evt);
        try {
            logException(evt.getThrowableProxy(), log);
            getLogCollection(level).insertOne(log);
        } catch (Exception e) {
            try {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                log.put("logging_error",
                        "Could not log all the event information: " + sw.toString());
                getLogCollection(level).insertOne(log);
            } catch (Exception e2) { // really not working
                addError("Could not insert log to mongo: " + evt, e2);
            }
        }
    }

    private Document getBasicLog(ILoggingEvent evt) {
        Document log = new Document();
        log.put("time",
                DateUtils.formatDate(new Date(evt.getTimeStamp()), DateUtils.YYYYMMDDHHMMSSSSS));

        // Store everything else inside a datamap
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("logger", evt.getLoggerName());
        Marker m = evt.getMarker();
        if (m != null) {
            dataMap.put("marker", m.getName());
        }
        Map<String, String> mdcMap = evt.getMDCPropertyMap();
        if (!mdcMap.isEmpty()) {
            Map<String, Object> mongoMdcMap = new HashMap<String, Object>();
            // Try to save a numeric value as Int
            Iterator<Entry<String, String>> it = mdcMap.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, String> pairs = it.next();
                if (isNumeric(pairs.getValue())) {
                    try {
                        Integer intValue = Integer.parseInt(pairs.getValue());
                        mongoMdcMap.put(pairs.getKey(), intValue);
                    } catch (NumberFormatException nfe) {
                        mongoMdcMap.put(pairs.getKey(), pairs.getValue());
                    }
                } else {
                    mongoMdcMap.put(pairs.getKey(), pairs.getValue());
                }
                it.remove(); // avoids a ConcurrentModificationException
            }

            dataMap.put("mdc", mongoMdcMap);
        }
        dataMap.put("message", evt.getFormattedMessage());

        log.put("data", dataMap);
        return log;
    }

    @SuppressWarnings("unchecked")
    private void logException(IThrowableProxy tp, Document log) {
        if (tp == null)
            return;
        String tpAsString = ThrowableProxyUtil.asString(tp); // the stack trace basically
        List<String> stackTrace =
                Arrays.asList(tpAsString.replace("\t", "").split(CoreConstants.LINE_SEPARATOR));
        if (stackTrace.size() > 0) {
            Map<String, Object> dataObject = (Map<String, Object>) log.get("d");
            dataObject.put("exception", stackTrace.get(0));
            if (stackTrace.size() > 1) {
                dataObject.put("stacktrace", stackTrace.subList(1, stackTrace.size()));
            }
            log.put("d", dataObject);
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    private boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }
}
