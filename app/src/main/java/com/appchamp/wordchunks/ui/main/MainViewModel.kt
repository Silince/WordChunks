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

package com.appchamp.wordchunks.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.appchamp.wordchunks.realmdb.models.pojo.PackJson
import com.appchamp.wordchunks.realmdb.models.realm.LevelState
import com.appchamp.wordchunks.realmdb.models.realm.PackState
import com.appchamp.wordchunks.realmdb.utils.gameModel
import com.appchamp.wordchunks.realmdb.utils.levelModel
import com.appchamp.wordchunks.realmdb.utils.packModel
import io.realm.Realm


class MainViewModel(application: Application?) : AndroidViewModel(application) {

    private val db: Realm = Realm.getDefaultInstance()

    fun initGame(packs: List<PackJson>) {
        if (packs.isEmpty()) return

        db.gameModel().createPacks(packs)
        // Initialize game state for the first time in the beginning.
        initFirstGameState()
    }

    private fun initFirstGameState() {
        val pack = db.packModel().findPackByState(PackState.LOCKED.value)
        pack?.let { db.packModel().setPackState(it, PackState.IN_PROGRESS.value) }
        val level = db.levelModel().findLevelByState(LevelState.LOCKED.value)
        level?.let { db.levelModel().setLevelState(it, LevelState.IN_PROGRESS.value) }
    }

    fun getLevelId(): String? {
        val level = db.levelModel().findLevelByState(LevelState.IN_PROGRESS.value)
        return level?.id
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     *
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel... Like RealmResults and the instance of Realm!
     */
    override fun onCleared() {
        db.close()
        super.onCleared()
    }
}