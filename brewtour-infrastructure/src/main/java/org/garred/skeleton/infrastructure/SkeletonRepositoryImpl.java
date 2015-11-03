package org.garred.skeleton.infrastructure;

import java.util.function.Consumer;

import org.garred.skeleton.api.SkelAggregate;
import org.garred.skeleton.api.SkelId;
import org.garred.skeleton.repository.SkeletonRepository;

import com.nullgeodesic.objectstore.Editor;
import com.nullgeodesic.objectstore.ObjectStore;
import com.nullgeodesic.objectstore.excp.ObjectDoesNotExistException;
import com.nullgeodesic.objectstore.excp.ObjectExistsException;
import com.nullgeodesic.objectstore.excp.ObjectStoreIOException;
import com.nullgeodesic.objectstore.excp.OptimisticLockingException;

public class SkeletonRepositoryImpl implements SkeletonRepository {

	private final ObjectStore objectStore;
	
	public SkeletonRepositoryImpl(ObjectStore objectStore) {
		this.objectStore = objectStore;
	}
	
	public void add(SkelId id, String value) {
		if(get(id) == null) {
			try {
				SkelAggregate skelAggregate = new SkelAggregate(id);
				skelAggregate.values.add(value);
				objectStore.save(id.id, skelAggregate);
			} catch (ObjectStoreIOException | ObjectExistsException e) {
				e.printStackTrace();
			}
		} else {
			try {
				Editor<SkelAggregate> editor = objectStore.edit(id.id, SkelAggregate.class);
				editor.get().values.add(value);
				editor.commit();
			} catch (ObjectStoreIOException | ObjectDoesNotExistException | OptimisticLockingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void save(SkelId key, SkelAggregate value) {
		try {
			objectStore.save(key.id, value);
		} catch (ObjectStoreIOException | ObjectExistsException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void put(SkelId key, SkelAggregate value) {
		try {
			objectStore.put(key.id, value);
		} catch (ObjectStoreIOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(SkelId key, SkelAggregate value) {
		try {
			Editor<SkelAggregate> editor = objectStore.edit(key.id, SkelAggregate.class);
			editor.set(value);
			editor.commit();
		} catch (ObjectStoreIOException | ObjectDoesNotExistException | OptimisticLockingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(SkelId key, Consumer<SkelAggregate> updater) {
		try {
			Editor<SkelAggregate> editor = objectStore.edit(key.id, SkelAggregate.class);
			updater.accept(editor.get());
			editor.commit();
		} catch (ObjectStoreIOException | ObjectDoesNotExistException | OptimisticLockingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SkelAggregate get(SkelId key) {
		try {
			return objectStore.get(key.id, SkelAggregate.class);
		} catch (ObjectStoreIOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(SkelId key) {
		try {
			objectStore.delete(key.id, SkelAggregate.class);
		} catch (ObjectStoreIOException e) {
			e.printStackTrace();
		}
	}

}
