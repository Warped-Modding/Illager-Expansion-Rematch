package me.sandbox.client.renders;

import me.sandbox.client.ModModelLayers;
import me.sandbox.client.model.IllagerWithStaffEntityModel;
import me.sandbox.entity.FirecallerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class FirecallerRender extends MobEntityRenderer<FirecallerEntity, IllagerWithStaffEntityModel<FirecallerEntity>> {
    private static final Identifier TEXTURE = new Identifier("illagerexp:textures/entity/firecaller.png");

    public FirecallerRender(EntityRendererFactory.Context context) {
        super(context, new IllagerWithStaffEntityModel<>(context.getPart(ModModelLayers.STAFF_ILLAGER)), 0.5f);
        this.model.getHat().visible = true;
    }
    protected void scale(final FirecallerEntity FirecallerEntity, final MatrixStack matrixStack, final float f) {
        matrixStack.scale(0.9f, 0.9f, 0.9f);
    }


    @Override
    public Identifier getTexture(FirecallerEntity entity) {
        return TEXTURE;
    }
}
