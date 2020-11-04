/*
 * Copyright 2020 Brackeys IDE contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.brackeys.ui.language.c.parser

import com.brackeys.ui.language.base.exception.ParseException
import com.brackeys.ui.language.base.model.ParseResult
import com.brackeys.ui.language.base.parser.LanguageParser

class CParser private constructor() : LanguageParser {

    companion object {

        private var cParser: CParser? = null

        fun getInstance(): CParser {
            return cParser ?: CParser().also {
                cParser = it
            }
        }
    }

    override fun execute(name: String, source: String): ParseResult {
        // TODO Implement parser
        val parseException = ParseException("Unable to parse unsupported language", 0, 0)
        return ParseResult(parseException)
    }
}