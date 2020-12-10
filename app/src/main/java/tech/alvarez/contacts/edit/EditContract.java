package tech.alvarez.contacts.edit;

import tech.alvarez.contacts.BasePresenter;
import tech.alvarez.contacts.BaseView;
import tech.alvarez.contacts.data.db.entity.Thing;

public interface EditContract {

    interface Presenter extends BasePresenter {
        void save(Thing person);

        boolean validate(Thing person);

        void getPersonAndPopulate(long id);

        void update(Thing person);




    }

    interface View extends BaseView<EditContract.Presenter> {

        void showErrorMessage(int field);

        void clearPreErrors();

        void close();

        void populate(Thing person);

        void takePhoto();
    }

}
