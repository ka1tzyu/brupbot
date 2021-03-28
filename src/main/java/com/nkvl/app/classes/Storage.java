package com.nkvl.app.classes;

import com.nkvl.app.App;
import com.nkvl.app.classes.expressions.TripleExpression;
import com.nkvl.app.classes.expressions.UnitExression;
import org.apache.log4j.Level;

import java.util.Dictionary;
import java.util.Hashtable;

public final class Storage {
    public final Dictionary<Long, TripleExpression[]> tripleExpTable;
    public final Dictionary<Long, UnitExression[]> unitExpTable;
    public final Dictionary<Long, Dictionary<String, String>> unitSymbolsTable;
    public final Dictionary<Long, ChallResult> resTable;

    public Storage() {
        tripleExpTable = new Hashtable<>();
        unitExpTable = new Hashtable<>();
        unitSymbolsTable = new Hashtable<>();
        resTable = new Hashtable<>();
    }

    public void genTripleExpressionSession(long id) {
        TripleExpression[] tmp = new TripleExpression[TripleExpression.getExpressionDefaultQuantity()];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = new TripleExpression();
        }

        tripleExpTable.put(id, tmp);
        ChallResult tmp1 = new ChallResult(System.nanoTime());
        resTable.put(id, tmp1);

        App.logger.log(Level.INFO, String.format("New expression session was created for [%d]", id));
    }

    public void genUnitExpressionSession() {}
}
