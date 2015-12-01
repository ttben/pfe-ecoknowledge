package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.LevelBuilderGettable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IAndable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IRewardable;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class AndOfImprovement extends LevelBuilderGettable implements IAndable {

    private Conditions conditions;

    public AndOfImprovement(Conditions conditions) {
        this.conditions = conditions;
    }


    @Override
    public IConditionsable and() {
        return conditions;
    }

    @Override
    public void end() {
        getLevel().end();
    }

    @Override
    public IRewardable atLevel(String levelName) {
        return getLevel().newLevel(levelName);
    }

    @Override
    protected Level getLevel() {
        return conditions.getLevel();
    }
}
