package com.licenta.databasedesignserver.factories

import com.licenta.databasedesignserver.enums.StatementType
import com.licenta.databasedesignserver.interfaces.IStatement
import com.licenta.databasedesignserver.resources.Oracle12cHtmlStatement
import com.licenta.databasedesignserver.resources.Oracle12cTextStatement

/**
 * Created by catal on 07-Apr-19.
 */
class Oracle12cStatementFactory {

    companion object {
        fun getStatement(statementType: StatementType): IStatement? =
                when (statementType) {
                    StatementType.Html -> Oracle12cHtmlStatement
                    StatementType.Text -> Oracle12cTextStatement
                }
    }
}