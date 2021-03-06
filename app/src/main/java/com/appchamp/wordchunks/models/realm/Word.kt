/*
 * Copyright 2017 Julia Kozhukhovskaya
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appchamp.wordchunks.models.realm

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required


@RealmClass
open class Word : RealmModel {

    @PrimaryKey
    @Required
    var id: String = ""
    @Required
    var levelId: String = ""
    @Required
    var word: String = ""
    var state: Int = 0  // 0 = not solved, 1 = solved
    var position: Int = 0
    var visibleLettersNum: Int = 1
}

// States of the word
val WORD_STATE_NOT_SOLVED = 0
val WORD_STATE_SOLVED = 1