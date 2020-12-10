package tech.alvarez.contacts.edit;

import tech.alvarez.contacts.data.db.dao.ThingDao;
import tech.alvarez.contacts.data.db.entity.Thing;
import tech.alvarez.contacts.utils.Constants;


public class EditPresenter implements EditContract.Presenter {


    private static final int CAMERA_REQUEST = 1888;
    private final EditContract.View mView;
    private final ThingDao thingDao;

    public EditPresenter(EditContract.View mMainView, ThingDao thingDao) {
        this.mView = mMainView;
        this.mView.setPresenter(this);
        this.thingDao = thingDao;
    }

    @Override
    public void start() {

    }

    @Override
    public void save(Thing person) {
        long ids = this.thingDao.insertPerson(person);
        mView.close();
    }

    @Override
    public boolean validate(Thing person) {
        mView.clearPreErrors();
        if (person.thingName.isEmpty()) {
            mView.showErrorMessage(Constants.FIELD_NAME);
            return false;
        }
        if (person.supplier.isEmpty()) {
            mView.showErrorMessage(Constants.FIELD_SUPPLIER);
            return false;
        }
        if (person.price.isEmpty()) {
            mView.showErrorMessage(Constants.FIELD_PRICE);
            return false;
        }
        if (person.quantity.isEmpty()) {
            mView.showErrorMessage(Constants.FIELD_QUANTITY);
            return false;
        }

        return true;
    }


    @Override
    public void getPersonAndPopulate(long id) {
        Thing person = thingDao.findPerson(id);
        if (person != null) {
            mView.populate(person);
        }
    }

    @Override
    public void update(Thing person) {
        int ids = this.thingDao.updatePerson(person);
        mView.close();
    }


}
