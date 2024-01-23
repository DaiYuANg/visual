package org.visual.model.component.animation;


import org.visual.model.component.util.algebradata.AlgebraData;

public interface AnimationStateTransferFinishCallback<T extends AlgebraData<T>> {
    void animationStateTransferFinish(AnimationNode<T> from, AnimationNode<T> to);
}
