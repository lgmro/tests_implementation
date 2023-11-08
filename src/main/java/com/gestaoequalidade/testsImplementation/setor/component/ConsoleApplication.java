package com.gestaoequalidade.testsImplementation.setor.component;

import com.gestaoequalidade.testsImplementation.setor.controller.SetorController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication implements CommandLineRunner {

    private final SetorController setorController;
    private final MenuHandlerInterface menuHandler;

    public ConsoleApplication(SetorController setorController, MenuHandlerInterface menuHandler) {
        this.setorController = setorController;
        this.menuHandler = menuHandler;
    }

    @Override
    public void run(String... args) throws Exception {
        menuHandler.handleMenu(setorController);
    }
}

