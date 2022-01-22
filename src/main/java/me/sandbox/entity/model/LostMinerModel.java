package me.sandbox.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.sandbox.entity.custom.LostMinerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;


public class LostMinerModel<T extends LostMinerEntity> extends EntityModel<T> {
    private final ModelRenderer waist;
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer hat;
    private final ModelRenderer rightArm;
    private final ModelRenderer rightItem;
    private final ModelRenderer leftArm;
    private final ModelRenderer leftItem;
    private final ModelRenderer rightLeg;
    private final ModelRenderer leftLeg;

    public LostMinerModel() {
        textureWidth = 64;
        textureHeight = 32;

        waist = new ModelRenderer(this);
        waist.setRotationPoint(0.0F, 12.0F, 0.0F);


        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, -12.0F, 0.0F);
        waist.addChild(body);
        body.setTextureOffset(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(head);
        head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        hat = new ModelRenderer(this);
        hat.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(hat);
        hat.setTextureOffset(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        body.addChild(rightArm);
        rightArm.setTextureOffset(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        rightItem = new ModelRenderer(this);
        rightItem.setRotationPoint(-1.0F, 7.0F, 1.0F);
        rightArm.addChild(rightItem);


        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        body.addChild(leftArm);
        leftArm.setTextureOffset(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, true);

        leftItem = new ModelRenderer(this);
        leftItem.setRotationPoint(1.0F, 7.0F, 1.0F);
        leftArm.addChild(leftItem);


        rightLeg = new ModelRenderer(this);
        rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        body.addChild(rightLeg);
        rightLeg.setTextureOffset(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        leftLeg = new ModelRenderer(this);
        leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        body.addChild(leftLeg);
        leftLeg.setTextureOffset(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, true);
    }
    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float rotateFactor;
        ItemStack itemstack = entityIn.getHeldItem(Hand.MAIN_HAND);
        rotateFactor = 1.0F;
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / rotateFactor;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / rotateFactor;
        if (entityIn.isAggressive() && (itemstack.isEmpty())) {
            float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
            this.leftArm.rotateAngleZ = 0.0F;
            this.rightArm.rotateAngleY = -(0.1F - f * 0.6F);
            this.leftArm.rotateAngleY = 0.1F - f * 0.6F;
            this.rightArm.rotateAngleX = (-(float) Math.PI / 2F);
            this.leftArm.rotateAngleX = (-(float) Math.PI / 2F);
            this.rightArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
            this.leftArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
            ModelHelper.func_239101_a_(this.rightArm, this.rightArm, ageInTicks);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        waist.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
