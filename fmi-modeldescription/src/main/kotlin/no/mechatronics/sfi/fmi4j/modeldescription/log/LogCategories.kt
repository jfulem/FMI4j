/*
 * The MIT License
 *
 * Copyright 2017-2018 Norwegian University of Technology
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING  FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package no.mechatronics.sfi.fmi4j.modeldescription.log

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.io.Serializable

/**
 * @author Lars Ivar Hatledal
 */
interface LogCategories: Iterable<LogCategory> {

    val size: Int
    operator fun contains(category: String): Boolean
    operator fun contains(category: LogCategory): Boolean

}

class LogCategoriesImpl : LogCategories, Serializable {

    override val size
        get() = categories.size

    override fun iterator(): Iterator<LogCategory>
            = categories.iterator()

    @JacksonXmlProperty(localName = "Category")
    @JacksonXmlElementWrapper(useWrapping = false)
    private val _categories: List<LogCategoryImpl>? = null

    private val categories: List<LogCategory>
        get() = _categories ?: emptyList()

    override fun contains(category: LogCategory)
            = categories.contains(category)

    override fun contains(category: String)
            = categories.map { it.name }.contains(category)

    override fun toString(): String {
        return "LogCategoriesImpl(size=$size, categories=$categories)"
    }

}