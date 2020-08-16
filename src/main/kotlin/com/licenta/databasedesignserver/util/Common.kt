package com.licenta.databasedesignserver.util

import org.apache.commons.lang3.RandomStringUtils
import java.util.*

/**
 * Created by catal on 09-Apr-19.
 */
class Common {
    companion object {
        fun createUUID(): String = RandomStringUtils.randomAlphabetic(10)
    }

}