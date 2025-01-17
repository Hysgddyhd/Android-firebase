/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.makeitso.model.service.impl

import com.example.makeitso.model.Good
import com.example.makeitso.model.Task
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.StorageService
import com.example.makeitso.model.service.trace
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
  StorageService {

  @OptIn(ExperimentalCoroutinesApi::class)
  override val tasks: Flow<List<Task>>
    //add listener to user id of collection,
    //will be emitted if current user sign out
    get() = auth.currentUser.flatMapLatest { user ->
      firestore.collection(TASK_COLLECTION).whereEqualTo(USER_ID_FIELD,user.id).dataObjects()
    }

  override suspend fun getTask(taskId: String): Task? =
    firestore.collection(TASK_COLLECTION).document(taskId).get().await().toObject()

  override suspend fun save(task: Task): String =
    trace(SAVE_TASK_TRACE) {
      val taskWithUserId = task.copy(userId = auth.currentUserId)
      firestore.collection(TASK_COLLECTION).add(taskWithUserId).await().id
    }

  override suspend fun update(task: Task): Unit =
    trace(UPDATE_TASK_TRACE) {
      firestore.collection(TASK_COLLECTION).document(task.id).set(task).await()
    }

  override suspend fun delete(taskId: String) {
    firestore.collection(TASK_COLLECTION).document(taskId).delete().await()
  }

  //my object
  @OptIn(ExperimentalCoroutinesApi::class)
  override val goods: Flow<List<Good>>
    //add listener to user id of collection,
    //will be emitted if current user sign out
    get() = auth.currentUser.flatMapLatest { user ->
      firestore.collection(GOOD_COLLECTION).whereEqualTo(USER_ID_FIELD,user.id).dataObjects()
    }


  override suspend fun getGood(goodId: String): Good? =
      firestore.collection(GOOD_COLLECTION).document(goodId).get().await().toObject()


  override suspend fun saveGood(good: Good): String =
    trace(SAVE_GOOD_TRACE) {
      val goodWithUserId = good.copy(userId = auth.currentUserId)
      firestore.collection(GOOD_COLLECTION).add(goodWithUserId).await().id
    }


  override suspend fun updateGood(good: Good) :Unit =
    trace(UPDATE_GOOD_TRACE) {
      firestore.collection(GOOD_COLLECTION).document(good.id).set(good).await()
    }

  override suspend fun deleteGood(goodId: String) {
    firestore.collection(GOOD_COLLECTION).document(goodId).delete().await()
  }


  companion object {
    private const val USER_ID_FIELD = "userId"

    private const val TASK_COLLECTION = "tasks"
    private const val SAVE_TASK_TRACE = "saveTask"
    private const val UPDATE_TASK_TRACE = "updateTask"
//my event
    private const val EVENT_COLLECTION = "events"
    private const val SAVE_EVENT_TRACE = "saveEvent"
    private const val UPDATE_EVENT_TRACE = "updateEvent"

    //my good
    private const val GOOD_COLLECTION = "goods"
    private const val SAVE_GOOD_TRACE = "saveGood"
    private const val UPDATE_GOOD_TRACE = "updateGood"

  }
}
