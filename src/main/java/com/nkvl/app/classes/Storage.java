package com.nkvl.app.classes;

import com.nkvl.app.App;
import com.nkvl.app.classes.expressions.TripleExpression;
import org.apache.log4j.Level;

import java.util.Dictionary;
import java.util.Hashtable;

public final class Storage {
    public Dictionary<Long, String> fsm;
    public Dictionary<Long, TripleExpression[]> expTable;
    public Dictionary<Long, ChallResult> resTable;

    public Storage() {
        fsm = new Hashtable<>();
        expTable = new Hashtable<>();
        resTable = new Hashtable<>();
    }

    public void genExpressionSession(long id) {
        TripleExpression[] tmp = new TripleExpression[TripleExpression.getExpressionDefaultQuantity()];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = new TripleExpression();
        }

        expTable.put(id, tmp);
        ChallResult tmp1 = new ChallResult(System.nanoTime());
        resTable.put(id, tmp1);

        App.logger.log(Level.INFO, String.format("New expression session was created for [%d]", id));
    }
}
