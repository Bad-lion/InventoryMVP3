package tech.alvarez.contacts.listedit;

import java.util.List;

import tech.alvarez.contacts.BasePresenter;
import tech.alvarez.contacts.BaseView;
import tech.alvarez.contacts.data.db.entity.Thing;

public interface ListContract {

    interface Presenter extends BasePresenter {

        void addNewPerson();

        void result(int requestCode, int resultCode);

        void populatePeople();

        void openEditScreen(Thing person);

        void openConfirmDeleteDialog(Thing person);

        void delete(long personId);
        void allDelete();
    }

    interface View extends BaseView<ListContract.Presenter> {

        void showAddPerson();

        void setPersons(List<Thing> persons);

        void showEditScreen(long id);

        void showDeleteConfirmDialog(Thing person);

        void showEmptyMessage();
    }

    interface OnItemClickListener {

        void clickItem(Thing person);

        void clickLongItem(Thing person);
    }

    interface DeleteListener {

        void setConfirm(boolean confirm, long personId);

    }
}
