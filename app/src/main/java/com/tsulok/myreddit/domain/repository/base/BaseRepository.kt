package com.tsulok.myreddit.domain.repository.base

import com.tsulok.myreddit.domain.repository.database.IRealmInstanceProvider
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery
import io.realm.RealmResults

interface IBaseDAOMapper {
    fun provideBaseDAOMapper(): BaseDAOMapper
}

open class BaseDAOMapper {
    val cNameId: String = "id"
}

/**
 * Base repository actions on db objects implementation
 * Creation / Deletion always creates a new realm which is closed right after a transaction
 * Read operands use the provided realm on the current thread
 * Update uses it's own realm to update the object itself
 */
abstract class BaseRepository<T>
constructor(private val realmInstanceProvider: IRealmInstanceProvider) : IBaseRepository<T>
        where T : RealmObject {

    override fun refresh(realm: Realm?) {
        return provideRealm(realm).refresh()
    }

    protected fun createBaseQuery(realm: Realm? = null): RealmQuery<T> {
        return provideRealm(realm).where(provideClass())
    }

    /**
     * Provides a realm instance, use existing ones if possible
     */
    protected fun provideRealm(realm: Realm?): Realm {
        return realm ?: realmInstanceProvider.provideRealmInstance()
    }

    /**
     * Provides a new realm always
     */
    protected fun provideNewRealm(realm: Realm?): Realm {
        return realm ?: realmInstanceProvider.provideNewRealmInstance()
    }

    private fun loadByIdBaseQuery(idKey: String, id: String?, realm: Realm?): RealmQuery<T> {
        return provideRealm(realm)
                .where(provideClass())
                .equalTo(idKey, id)
    }

    override fun loadByIdRx(id: String?, realm: Realm?): Flowable<RealmResults<T>> {
        return loadByIdBaseQuery(provideMapper().provideBaseDAOMapper().cNameId,
                id, realm)
                .findAll()
                .asFlowable()
    }

    override fun loadById(id: String?, realm: Realm?): T? {
        return loadByIdBaseQuery(provideMapper().provideBaseDAOMapper().cNameId,
                id, realm)
                .findFirst()
    }

    override fun insert(item: T, realm: Realm?) {
        val usedRealm = provideNewRealm(realm)
        usedRealm.executeTransaction { realmInstance ->
            realmInstance.insertOrUpdate(item)
        }
        usedRealm.close()
    }

    override fun insertAll(items: List<T>, realm: Realm?) {
        val usedRealm = provideNewRealm(realm)
        usedRealm.executeTransaction { realmInstance ->
            realmInstance.insertOrUpdate(items)
        }
        usedRealm.close()
    }

    override fun update(item: T) {
        provideRealm(item.realm)
                .executeTransaction { realmInstance ->
                    realmInstance.insertOrUpdate(item)
                }
    }

    override fun update(item: T, updateBlock: (Realm) -> Unit) {
        provideRealm(item.realm)
                .executeTransaction { realm ->
                    updateBlock(realm)
                    realm.insertOrUpdate(item)
                }
    }

    override fun delete(item: T, realm: Realm?) {
        val usedRealm = provideNewRealm(realm)
        usedRealm.executeTransaction {
            item.deleteFromRealm()
        }
        usedRealm.close()
    }

    override fun listAllItems(realm: Realm?): RealmResults<T> {
        return provideRealm(realm)
                .where(provideClass())
                .findAll()
    }

    override fun listAllItemsRx(realm: Realm?): Flowable<RealmResults<T>> {
        return provideRealm(realm)
                .where(provideClass())
                .findAll()
                .asFlowable()
    }

    override fun listItems(ids: List<String>, realm: Realm?, forceRefresh: Boolean): List<T> {
        val realm = provideRealm(realm)

        if (forceRefresh) {
            refresh(realm)
        }

        return realm
                .where(provideClass())
                .`in`(provideMapper().provideBaseDAOMapper().cNameId, ids.toTypedArray())
                .findAll()
    }

    override fun listItemsRx(ids: List<String>, realm: Realm?): Flowable<RealmResults<T>> {
        return provideRealm(realm)
                .where(provideClass())
                .`in`(provideMapper().provideBaseDAOMapper().cNameId, ids.toTypedArray())
                .findAll()
                .asFlowable()
    }

    override fun removeItems(ids: List<String>, realm: Realm?) {
        val usedRealm = provideNewRealm(realm)
        usedRealm.executeTransaction { realmInstance ->
            realmInstance.where(provideClass())
                    .`in`(provideMapper().provideBaseDAOMapper().cNameId, ids.toTypedArray())
                    .findAll()
                    .deleteAllFromRealm()
        }
        usedRealm.close()
    }

    override fun removeAll(realm: Realm?) {
        val usedRealm = provideNewRealm(realm)
        usedRealm.executeTransaction { realmInstance ->
            realmInstance.where(provideClass())
                    .findAll()
                    .deleteAllFromRealm()
        }
        usedRealm.close()
    }

    abstract fun provideClass(): Class<T>
    abstract fun provideMapper(): IBaseDAOMapper
}