package org.noses.cirsect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class InstructionsScreen implements Screen {
    private Stage stage;
    private Table table;

    private TextArea textArea;

    private CirsectGame parent;

    public InstructionsScreen(CirsectGame parent) {
        this.parent = parent;
    }

    public void create() {
        Skin skin = parent.game.getSkin();

        stage = new Stage();

        table = new Table();
        table.setFillParent(true);
        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight());
        //table.setDebug(true);

        stage.addActor(table);

        String text = "\n" +
                "\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean tristique dolor gravida, pharetra eros at, aliquam erat. Vivamus tempus, nunc vel fringilla tincidunt, massa nibh suscipit nisl, faucibus rhoncus velit quam sit amet nisl. Mauris porttitor elit vel feugiat sagittis. In lacinia, mi eu porttitor hendrerit, ex mi porta arcu, in ultrices urna purus sed sapien. Nunc aliquet dictum mauris ut hendrerit. Sed efficitur, ligula sit amet porta ultrices, tortor turpis maximus diam, sed rhoncus elit nulla ut ipsum. Duis rhoncus fermentum dui, tincidunt posuere eros maximus et. Praesent leo ipsum, tincidunt nec rhoncus in, fringilla eu sapien.\n" +
                "\n" +
                "Aliquam efficitur nunc sed arcu gravida, sit amet viverra diam molestie. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Curabitur luctus luctus pretium. Sed viverra sem nulla, et iaculis dui venenatis ac. Morbi finibus bibendum dui venenatis cursus. Integer sit amet euismod mauris. Praesent gravida maximus quam, egestas vehicula felis. Phasellus scelerisque molestie tortor, viverra lacinia enim vestibulum eget. Aenean non ornare orci, nec varius metus. Nam ac maximus ante, quis placerat velit. Donec fringilla sit amet ipsum nec congue.\n" +
                "\n" +
                "Suspendisse eget elit sit amet lectus lacinia dignissim. Phasellus urna libero, auctor eget urna sit amet, vulputate ultrices felis. Cras nec condimentum mauris. Ut laoreet dui dolor, vel consectetur lorem suscipit et. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nullam egestas id mauris ut dapibus. Cras vitae tempor enim. Donec mattis dolor aliquam, commodo urna vulputate, pulvinar dolor. Donec at magna est. Phasellus quis arcu non nibh rhoncus tristique eu eget risus. Sed elementum cursus lectus vitae tincidunt. Nulla euismod lacus eu velit lobortis, ac rutrum nisl mollis. Aenean commodo justo lorem, a ultricies ex dapibus vel. Ut consectetur iaculis odio. Ut pellentesque felis eget efficitur iaculis. Aenean pulvinar ultricies pharetra.\n" +
                "\n" +
                "Curabitur consequat vestibulum enim eu luctus. Nam porttitor erat id leo malesuada cursus. Maecenas consequat, nisi eu bibendum sagittis, diam nibh elementum dui, a tempor ligula odio id mi. Pellentesque condimentum odio tristique, sollicitudin nisl sed, aliquam odio. Pellentesque at risus consequat, semper neque vel, gravida erat. Pellentesque interdum lacinia nisi. Integer sollicitudin felis ipsum, quis ultrices quam congue eu. In a molestie dolor. Maecenas velit massa, tristique sed metus vel, pulvinar dapibus libero. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nullam non volutpat lorem. Vestibulum tempor auctor arcu. Fusce vel pretium quam, eget interdum ante. Fusce lacinia elit in enim consectetur sagittis. Nam elementum pretium ultrices.\n" +
                "\n" +
                "Nullam feugiat pharetra bibendum. Sed egestas lorem a ipsum ullamcorper condimentum. Ut tempor magna a fringilla rutrum. Donec id magna scelerisque metus viverra cursus quis sed massa. Sed aliquet dui felis, eu tristique enim vulputate nec. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla facilisi. Pellentesque quis est fringilla est lacinia fringilla quis eget eros. Pellentesque at ante mattis leo iaculis consectetur. Ut rhoncus bibendum diam id commodo. ";
        try {
            text = Gdx.files.internal("instructions.txt").readString();
        } catch (Exception anyExc) {
            anyExc.printStackTrace();
        }

        textArea = new TextArea(text, skin);

        TextField.TextFieldStyle textAreaStyle = skin.get(TextField.TextFieldStyle.class);
        textAreaStyle.font.getData().setScale(.18f/parent.smallestDimensionPercent);
        textAreaStyle.font.getData().markupEnabled = true;
        textArea.setFillParent(false);
        textArea.setHeight(10);

        table.defaults().expandX().expandY().space(4);

        table.row();
        table.add(textArea).width(Gdx.graphics.getWidth()).height((int)(Gdx.graphics.getHeight()*.8));

        Button button = new Button(skin);
        button.add(new Label("Main Menu", skin));

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("button clicked");
                parent.mainMenu();
            }
        });

        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("TouchDown!");
                parent.mainMenu();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        table.row();
        table.add(button);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void dispose() {
        stage.dispose();
    }
}
