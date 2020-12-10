package tech.alvarez.contacts.listedit;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.List;

import tech.alvarez.contacts.data.db.dao.ThingDao;
import tech.alvarez.contacts.data.db.entity.Thing;

public class ListPresenter implements ListContract.Presenter {

    private final ListContract.View mView;
    private final ThingDao thingDao;

    public ListPresenter(ListContract.View view, ThingDao thingDao) {
        this.mView = view;
        this.mView.setPresenter(this);
        this.thingDao = thingDao;
    }

    @Override
    public void start() {

    }

    @Override
    public void addNewPerson() {
        mView.showAddPerson();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void populatePeople() {
        thingDao.findAllPersons().observeForever(new Observer<List<Thing>>() {
            @Override
            public void onChanged(@Nullable List<Thing> persons) {
                mView.setPersons(persons);
                if (persons == null || persons.size() < 1) {
                    mView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void openEditScreen(Thing person) {
        mView.showEditScreen(person.id);
    }

    @Override
    public void openConfirmDeleteDialog(Thing person) {
        mView.showDeleteConfirmDialog(person);
    }

    @Override
    public void delete(long personId) {
        Thing person = thingDao.findPerson(personId);
        thingDao.deletePerson(person);
    }

    @Override
    public void allDelete() {
        thingDao.deleteAll();
    }
}
