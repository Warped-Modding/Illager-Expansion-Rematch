package me.sandbox.client.renders;

import me.sandbox.client.ModModelLayers;
import me.sandbox.client.model.CapedIllagerEntityModel;
import me.sandbox.client.model.HatIllagerEntityModel;
import me.sandbox.entity.SorcererEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class SorcererRender extends MobEntityRenderer<SorcererEntity, HatIllagerEntityModel<SorcererEntity>> {
    private static final Identifier TEXTURE = new Identifier("sandbox:textures/entity/sorcerer.png");

    public SorcererRender(EntityRendererFactory.Context context) {
        super(context, new HatIllagerEntityModel<>(context.getPart(ModModelLayers.HAT_ILLAGER)), 0.5f);
        this.model.getHat().visible = true;
    }

    @Override
    public Identifier getTexture(SorcererEntity entity) {
        return TEXTURE;
    }
}
