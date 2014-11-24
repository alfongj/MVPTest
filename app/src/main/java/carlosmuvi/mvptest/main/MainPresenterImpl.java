package carlosmuvi.mvptest.main;

import java.util.List;

import carlosmuvi.mvptest.entities.Beer;

/**
 * Created by Carlos on 24/11/2014.
 */
public class MainPresenterImpl implements MainPresenter, OnBeersFinishedListener {

    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenterImpl(MainView mainView) {
        this.mainInteractor = new MainInteractorImpl();
        this.mainView = mainView;
    }

    @Override
    public void getBeers() {
        mainView.showLoading("Loading", "Loading beers, please wait");
        mainInteractor.getBeers(this);
    }

    @Override
    public void onBeersSuccess(List<Beer> beers) {
        mainView.hideLoading();
        mainView.fillBeerList(beers);
        mainView.showMsg("Showing " + beers.size() + " beers!");
    }

    @Override
    public void onBeersError(String errorMsg) {
        mainView.hideLoading();
        mainView.showMsg(errorMsg);
    }
}
