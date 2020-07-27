package us.dontcareabout.avMine.client.component;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.GridView;

import us.dontcareabout.avMine.shared.vo.Film;
import us.dontcareabout.gxt.client.component.Grid2;

public class FilmGrid extends Grid2<Film> {
	private static final Properties properties = GWT.create(Properties.class);

	public FilmGrid() {
		init();
	}

	@Override
	protected ListStore<Film> genListStore() {
		ListStore<Film> result = new ListStore<>(properties.key());
		return result;
	}

	@Override
	protected ColumnModel<Film> genColumnModel() {
		ArrayList<ColumnConfig<Film, ?>> list = new ArrayList<>();
		list.add(new ColumnConfig<>(properties.name(), 50, "名稱"));
		list.add(new ColumnConfig<>(properties.size(), 50, "檔案大小"));
		list.add(new ColumnConfig<>(properties.viewCount(), 50, "觀看次數"));
		list.add(new ColumnConfig<>(properties.score(), 50, "分數"));
		return new ColumnModel<>(list);
	}

	@Override
	protected GridView<Film> genGridView() {
		GridView<Film> result = new GridView<>();
		result.setForceFit(true);
		return result;
	}

	interface Properties extends PropertyAccess<Film> {
		@Path("id") ModelKeyProvider<Film> key();
		ValueProvider<Film, String> name();
		ValueProvider<Film, Long> size();
		ValueProvider<Film, Date> createTime();
		ValueProvider<Film, Integer> viewCount();
		ValueProvider<Film, Integer> score();
	}
}
