package cn.promptness.fx;

import cn.promptness.fx.data.Constant;
import cn.promptness.fx.utils.SpringFxmlLoader;
import cn.promptness.fx.utils.SystemTrayUtil;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class FxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        if (!SystemTray.isSupported()) {
            System.exit(1);
        }
        Application.launch(FxApplication.class, args);
    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder().sources(FxApplication.class).bannerMode(Banner.Mode.OFF).web(WebApplicationType.NONE).run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) {
        SystemTrayUtil.systemTray(primaryStage, Constant.TITLE);
        Parent root = applicationContext.getBean(SpringFxmlLoader.class).load("/fxml/index.fxml");
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle(Constant.TITLE);
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
