package org.noses.cirsect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StartScreen {
    Stage stage;
    CirsectGame parent;

    Button startButton;
    Button instructionsButton;

    Image backgroundImage;

    public StartScreen(CirsectGame parent, Integer lastGameScore) {
        this.parent = parent;

        stage = new Stage(new ScreenViewport());

        Texture backgroundTexture = new Texture("logo.png");

        backgroundImage = new Image (backgroundTexture);
        backgroundImage.setX(Gdx.graphics.getWidth()*.1f);
        backgroundImage.setY(Gdx.graphics.getHeight()*.5f);
        backgroundImage.setHeight(Gdx.graphics.getHeight()*.4f);
        backgroundImage.setWidth(Gdx.graphics.getWidth()*.8f);
        backgroundImage.setFillParent(false);

        stage.addActor(backgroundImage);

        Skin skin = new Skin(Gdx.files.internal("skin/comic/skin/comic-ui.json"));

        TextButton.TextButtonStyle buttonStyle = skin.get(TextButton.TextButtonStyle.class);
        buttonStyle.font.getData().setScale(.15f/parent.smallestDimensionPercent);

        startButton = new TextButton("Start", skin);

        startButton.setWidth(Gdx.graphics.getWidth() * .4f);
        startButton.setHeight(Gdx.graphics.getWidth() * .2f);

        startButton.setX((float)((stage.getWidth()- startButton.getWidth())*.5) );
        startButton.setY(stage.getHeight()*.1f);
        stage.addActor(startButton);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startButtonClicked();
            }
        });

        instructionsButton = new TextButton("Instructions", skin);

        instructionsButton.setWidth(Gdx.graphics.getWidth() * .6f);
        instructionsButton.setHeight(Gdx.graphics.getWidth() * .2f);

        instructionsButton.setX((float)((stage.getWidth()- instructionsButton.getWidth())*.5));
        instructionsButton.setY(stage.getHeight()*.3f);
        stage.addActor(instructionsButton);

        instructionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                instructionsButtonClicked();
            }
        });

        start();
    }

    public void start() {
        Gdx.input.setInputProcessor(stage);
    }

    public void startButtonClicked() {
        parent.startGame();
    }

    public void instructionsButtonClicked() {
        parent.instructions();
    }

    public void render(Batch batch) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
}
