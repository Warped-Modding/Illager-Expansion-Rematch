package me.sandbox.client.renders;

import me.sandbox.client.renders.feature.InvokerShieldFeatureRender;
import me.sandbox.client.renders.feature.NecromancerShieldFeatureRender;
import me.sandbox.entity.NecromancerEntity;
import me.sandbox.entity.NecromancerEntity;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.EnchantingTableBlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

public class NecromancerRender extends IllagerEntityRenderer<NecromancerEntity> {
    private static final Identifier TEXTURE = new Identifier("sandbox:textures/entity/necromancer.png");


    public NecromancerRender(EntityRendererFactory.Context context) {

        super(context, new IllagerEntityModel(context.getPart(EntityModelLayers.EVOKER)), 0.5f);
        this.addFeature(new NecromancerShieldFeatureRender(this, context.getModelLoader()));
        this.addFeature(new HeldItemFeatureRenderer<NecromancerEntity, IllagerEntityModel<NecromancerEntity>>((FeatureRendererContext)this){


            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, NecromancerEntity NecromancerEntity, float f, float g, float h, float j, float k, float l) {

                if (NecromancerEntity.isSpellcasting()) {
                    super.render(matrixStack, vertexConsumerProvider, i, NecromancerEntity, f, g, h, j, k, l);
                }
            }
        });
        ((IllagerEntityModel)this.model).getHat().visible = false;
    }


    @Override
    public Identifier getTexture(NecromancerEntity entity) {
        return TEXTURE;
    }
}