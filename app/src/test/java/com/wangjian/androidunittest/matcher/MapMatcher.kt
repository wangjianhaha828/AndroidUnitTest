package com.wangjian.androidunittest.matcher

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Factory
import org.hamcrest.Matcher

class MapMatcher<T>(value: T): BaseMatcher<T>() {

    private val expectedValue: Any? = value

    override fun matches(mutableMap: Any?): Boolean {
        return mapHasValue(mutableMap,expectedValue)
    }



    override fun describeTo(description: Description?) {
        description?.appendValue(expectedValue)
    }

    companion object{
        @Factory
        fun <T> hasValue(operand: T): Matcher<T>? {
            return MapMatcher(operand)
        }

        private fun mapHasValue(mutableMap: Any?, expectedValue: Any?): Boolean {
            mutableMap?.let {
                if (mutableMap is MutableMap<*, *>){
                    if (mutableMap.containsValue(expectedValue)){
                        return true
                    }
                }
            }
            return false
        }
    }
}