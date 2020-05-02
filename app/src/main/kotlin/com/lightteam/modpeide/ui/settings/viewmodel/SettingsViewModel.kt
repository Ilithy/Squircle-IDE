/*
 * Licensed to the Light Team Software (Light Team) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Light Team licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lightteam.modpeide.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lightteam.modpeide.R
import com.lightteam.modpeide.data.storage.keyvalue.PreferenceHandler
import com.lightteam.modpeide.data.utils.extensions.schedulersIoToMain
import com.lightteam.modpeide.domain.providers.rx.SchedulersProvider
import com.lightteam.modpeide.ui.base.viewmodel.BaseViewModel
import com.lightteam.modpeide.ui.settings.adapter.item.FontItem
import com.lightteam.modpeide.ui.settings.adapter.item.PreferenceItem
import com.lightteam.modpeide.utils.event.SingleLiveEvent
import com.lightteam.modpeide.data.utils.commons.Fonts
import io.reactivex.rxkotlin.subscribeBy

class SettingsViewModel(
    private val schedulersProvider: SchedulersProvider,
    private val preferenceHandler: PreferenceHandler
) : BaseViewModel() {

    val fullscreenEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val headersEvent: SingleLiveEvent<List<PreferenceItem>> = SingleLiveEvent()
    val fontsEvent: SingleLiveEvent<List<FontItem>> = SingleLiveEvent()
    val selectionEvent: SingleLiveEvent<String> = SingleLiveEvent()

    fun fetchHeaders() {
        headersEvent.value = listOf(
            PreferenceItem(
                R.string.pref_header_application_title,
                R.string.pref_header_application_summary,
                R.id.applicationFragment
            ),
            PreferenceItem(
                R.string.pref_header_editor_title,
                R.string.pref_header_editor_summary,
                R.id.editorFragment
            ),
            PreferenceItem(
                R.string.pref_header_codeStyle_title,
                R.string.pref_header_codeStyle_summary,
                R.id.codeStyleFragment
            ),
            PreferenceItem(
                R.string.pref_header_files_title,
                R.string.pref_header_files_summary,
                R.id.filesFragment
            ),
            PreferenceItem(
                R.string.pref_header_about_title,
                R.string.pref_header_about_summary,
                R.id.aboutFragment
            )
        )
    }

    fun fetchFonts() {
        fontsEvent.value = listOf(
            FontItem(
                "Droid Sans Mono",
                Fonts.DROID_SANS_MONO,
                supportLigatures = false,
                isPaid = false
            ),
            FontItem(
                "JetBrains Mono",
                Fonts.JETBRAINS_MONO,
                supportLigatures = true,
                isPaid = false
            ),
            FontItem(
                "Fira Code",
                Fonts.FIRA_CODE,
                supportLigatures = true,
                isPaid = true
            ),
            FontItem(
                "Source Code Pro",
                Fonts.SOURCE_CODE_PRO,
                supportLigatures = false,
                isPaid = true
            ),
            FontItem(
                "Anonymous Pro",
                Fonts.ANONYMOUS_PRO,
                supportLigatures = false,
                isPaid = true
            ),
            FontItem(
                "DejaVu Sans Mono",
                Fonts.DEJAVU_SANS_MONO,
                supportLigatures = false,
                isPaid = true
            )
        )
    }

    fun selectFont(fontItem: FontItem) {
        preferenceHandler.getFontType().set(fontItem.fontPath)
        selectionEvent.value = fontItem.fontName
    }

    fun observePreferences() {
        preferenceHandler.getFullscreenMode()
            .asObservable()
            .schedulersIoToMain(schedulersProvider)
            .subscribeBy { fullscreenEvent.value = it }
            .disposeOnViewModelDestroy()
    }

    class Factory(
        private val schedulersProvider: SchedulersProvider,
        private val preferenceHandler: PreferenceHandler
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return when {
                modelClass === SettingsViewModel::class.java ->
                    SettingsViewModel(
                        schedulersProvider,
                        preferenceHandler
                    ) as T
                else -> null as T
            }
        }
    }
}