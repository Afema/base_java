package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(Integer) index];
    }


    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }


    @Override
    protected void doUpdate(Resume r, Object index) {
        storage[(Integer) index] = r;
    }
    

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected void doSave(Resume r, Object index) {
        if(size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertElement(r, (Integer) index);
            storage[size] = r;
            size++;
        }
    }

    @Override
    public void doDelete(Object index) {
            fillDeletedElement((Integer) index);
            storage[size - 1] = null;
            size--;
        }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >=0;
    }

    protected abstract void insertElement(Resume r, int index);
    protected abstract void fillDeletedElement(int index);
    protected abstract Integer getSearchKey(String uuid);

}