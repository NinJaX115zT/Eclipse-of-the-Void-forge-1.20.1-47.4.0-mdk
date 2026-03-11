package net.lucarioninja.eclipseofthevoid.worldgen.tree.decorator;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTreeDecorators {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS =
            DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, EclipseOfTheVoid.MOD_ID);

    public static final RegistryObject<TreeDecoratorType<EtherealNestDecorator>> ETHEREAL_NEST_DECORATOR =
            TREE_DECORATORS.register("ethereal_nest_decorator",
                    () -> new TreeDecoratorType<>(EtherealNestDecorator.CODEC));
}
