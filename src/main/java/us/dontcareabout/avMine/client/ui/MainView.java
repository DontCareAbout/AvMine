package us.dontcareabout.avMine.client.ui;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.ResizeContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

import us.dontcareabout.avMine.client.component.FilmGrid;
import us.dontcareabout.avMine.client.component.player.VideoPlayer;
import us.dontcareabout.avMine.client.data.DataCenter;
import us.dontcareabout.avMine.client.data.FilmReadyEvent;
import us.dontcareabout.avMine.client.data.FilmReadyEvent.FilmReadyHandler;
import us.dontcareabout.avMine.shared.vo.Film;

public class MainView extends SimpleContainer {
	private static final HorizontalLayoutData GRID_HLD = new HorizontalLayoutData(300, 1);
	private static final HorizontalLayoutData PLAYER_HLD = new HorizontalLayoutData(1, 1);
	private static final VerticalLayoutData GRID_VLD = new VerticalLayoutData(1, 300);
	private static final VerticalLayoutData PLAYER_VLD = new VerticalLayoutData(1, 1);

	private final FilmGrid filmGrid = new FilmGrid();
	private final VideoPlayer player = new VideoPlayer();

	private ResizeContainer resizeContainer;
	private boolean isLangscape = false;

	public MainView() {
		build();
		filmGrid.getSelectionModel().addSelectionHandler(new SelectionHandler<Film>() {
			@Override
			public void onSelection(SelectionEvent<Film> event) {
				player.setSrc("video/" + event.getSelectedItem().getId());
				player.play();
			}
		});

		DataCenter.addFilmReady(new FilmReadyHandler() {
			@Override
			public void onFilmReady(FilmReadyEvent event) {
				filmGrid.getStore().addAll(DataCenter.getFilm());
			}
		});
	}

	@Override
	protected void onResize(int width, int height) {
		boolean isChange = false;

		if (width > height && !isLangscape) {
			isLangscape = true;
			isChange = true;
		}

		if (width < height && isLangscape) {
			isLangscape = false;
			isChange = true;
		}

		if (isChange) {
			remove(resizeContainer);
			build();
		}

		super.onResize(width, height);
	}

	private void build() {
		if (isLangscape) {
			HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
			hlc.add(filmGrid, GRID_HLD);
			hlc.add(player, PLAYER_HLD);
			resizeContainer = hlc;
		} else {
			VerticalLayoutContainer vlc = new VerticalLayoutContainer();
			vlc.add(filmGrid, GRID_VLD);
			vlc.add(player, PLAYER_VLD);
			resizeContainer = vlc;
		}

		add(resizeContainer);
	}
}
