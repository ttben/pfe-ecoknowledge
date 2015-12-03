package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.LevelBuilderGettable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsableAfterReward;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IRewardable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IRewardableWithIcon;

/**
 * Created by Sébastien on 27/11/2015.
 */
public class Rewards extends LevelBuilderGettable implements IRewardableWithIcon {

    Level l;

    public Rewards(Level level) {
        this.l = level;
    }


    @Override
    public IConditionsableAfterReward rewards(Integer points) {
        l.setPoints(points);
        return new ConditionBuilder(this);
    }

    @Override
    public IRewardable withImage(String url) {
        l.setImage(url);
        return this;
    }

    @Override
    protected Level getLevel() {
        return l;
    }
}
