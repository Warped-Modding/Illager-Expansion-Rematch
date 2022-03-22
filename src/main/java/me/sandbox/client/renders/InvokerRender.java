package me.sandbox.client.renders;

import me.sandbox.client.ModModelLayers;
import me.sandbox.client.model.CapedIllagerEntityModel;
import me.sandbox.client.renders.feature.InvokerShieldFeatureRender;
import me.sandbox.entity.InvokerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.WitherArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

    public class InvokerRender extends MobEntityRenderer<InvokerEntity, CapedIllagerEntityModel<InvokerEntity>> {
        private static final Identifier TEXTURE = new Identifier("illagerexp:textures/entity/caped_invoker.png");

        public InvokerRender(EntityRendererFactory.Context context) {
            super(context, new CapedIllagerEntityModel<>(context.getPart(ModModelLayers.CAPED_ILLAGER)), 0.5f);
            this.addFeature(new InvokerShieldFeatureRender(this, context.getModelLoader()));
        }

        @Override
        public Identifier getTexture(InvokerEntity entity) {
                return TEXTURE;
            }
        }
