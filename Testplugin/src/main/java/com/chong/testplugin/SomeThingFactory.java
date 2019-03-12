package com.chong.testplugin;

import com.chong.aidllibrary.Animal;
import com.chong.aidllibrary.AnimalFactory;

public class SomeThingFactory implements AnimalFactory {
    @Override
    public Animal newInstance() {
        return new SomeThing();
    }
}
