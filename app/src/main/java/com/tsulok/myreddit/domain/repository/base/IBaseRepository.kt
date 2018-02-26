package com.tsulok.myreddit.domain.repository.base

import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmResults

/**
 * Base repository actions on db objects
 */
interface IBaseRepository<T> {

    /**
     * Force refresh the dataset
     */
    fun refresh(realm: Realm? = null)

    /**
     * Returns data with the given primary key
     *
     * @param id The primary key to be searched with
     * @param realm The realm instance to execute the action
     * @return The data if found or null
     */
    fun loadById(id: String?, realm: Realm? = null): T?

    /**
     * Returns data with the given primary key
     *
     * @param id The primary key to be searched with
     * @param realm The realm instance to execute the action
     * @return The data if found or null
     */
    fun loadByIdRx(id: String?, realm: Realm? = null): Flowable<RealmResults<T>>

    /**
     * Inserts a new or replace the existing item in the database
     *
     * @param item The item to be inserted or replaced
     * @param realm The realm instance to execute the action
     */
    fun insert(item: T, realm: Realm? = null)

    /**
     * Insert new items in the database
     *
     * @param items The items to be inserted
     * @param realm The realm instance to execute the action
     */
    fun insertAll(items: List<T>, realm: Realm? = null)

    /**
     * Updates an existing item in the database
     *
     * @param item The item to be updated
     */
    fun update(item: T)

    /**
     * Updates an existing item in the database
     *
     * @param item The item to be updated
     * @param updateBlock Realm scoped write transaction block
     */
    fun update(item: T, updateBlock: (Realm) -> Unit)

    /**
     * Deletes the item from the database
     *
     * @param item The item to be deleted
     * @param realm The realm instance to execute the action
     */
    fun delete(item: T, realm: Realm? = null)

    /**
     * List all items for this type
     *
     * @param realm The realm instance to execute the action
     * @return Items for this type
     */
    fun listAllItems(realm: Realm? = null): RealmResults<T>

    /**
     * List all items for this type
     *
     * @param realm The realm instance to execute the action
     * @return Items for this type
     */
    fun listAllItemsRx(realm: Realm? = null): Flowable<RealmResults<T>>

    /**
     * @param realm The realm instance to execute the action
     * @return List items for this type specified with the ids list
     */
    fun listItems(ids: List<String>, realm: Realm? = null, forceRefresh: Boolean = false): List<T>

    /**
     * @param realm The realm instance to execute the action
     * @return List items for this type specified with the ids list
     */
    fun listItemsRx(ids: List<String>, realm: Realm? = null): Flowable<RealmResults<T>>

    /**
     * @param realm The realm instance to execute the action
     * Remove the items for this type specified with the ids list
     */
    fun removeItems(ids: List<String>, realm: Realm? = null)

    /**
     * @param realm The realm instance to execute the action
     * Removes all entities for this entity type
     */
    fun removeAll(realm: Realm? = null)
}