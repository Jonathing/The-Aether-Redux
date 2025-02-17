package net.zepalesque.redux.client.render.entity.model.entity;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.entity.passive.Glimmercow;

@OnlyIn(Dist.CLIENT)
public class GlimmercowModel<T extends Glimmercow> extends QuadrupedModel   <T> {
   public GlimmercowModel(ModelPart root) {
      super(root, false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();

      PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 2.0F));

      PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(31, 35).addBox(-4.0F, 2.0F, -11.0F, 8.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
              .texOffs(0, 0).addBox(-8.0F, -10.0F, -10.0F, 16.0F, 18.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

      PartDefinition mush1 = body.addOrReplaceChild("mush1", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, -7.0F, 5.0F, 0.0F, -1.7017F, 0.0F));

      PartDefinition mushroom1_r1 = mush1.addOrReplaceChild("mushroom1_r1", CubeListBuilder.create().texOffs(1, 43).addBox(-3.5F, -12.0F, -7.5F, 0.0F, 8.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 8.0F, -2.0F, 0.0F, 0.7854F, 0.0F));

      PartDefinition mushroom1_r2 = mush1.addOrReplaceChild("mushroom1_r2", CubeListBuilder.create().texOffs(1, 43).addBox(-1.0F, -12.0F, -3.0F, 0.0F, 8.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 8.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

      PartDefinition mush2 = body.addOrReplaceChild("mush2", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -8.0F, -6.0F, 0.0F, -1.4399F, 0.0F));

      PartDefinition mushroom2_r1 = mush2.addOrReplaceChild("mushroom2_r1", CubeListBuilder.create().texOffs(32, 40).addBox(-2.5F, -12.0F, -13.5F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 8.0F, 6.0F, 0.0F, -0.7854F, 0.0F));

      PartDefinition mushroom2_r2 = mush2.addOrReplaceChild("mushroom2_r2", CubeListBuilder.create().texOffs(32, 40).addBox(5.5F, -12.0F, -10.5F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 8.0F, 6.0F, 0.0F, 0.7854F, 0.0F));

      PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -5.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
              .texOffs(0, 0).mirror().addBox(-7.0F, -8.0F, -4.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 10.0F, -8.0F));

      PartDefinition hornL_r1 = head.addOrReplaceChild("hornL_r1", CubeListBuilder.create().texOffs(0, 0).addBox(4.0F, -27.0F, -12.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, 8.0F, 0.0F, 0.0436F, 0.0F));

      PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(40, 42).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, 7.0F));

      PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 15.0F, 7.0F));

      PartDefinition leg1_r1 = right_hind_leg.addOrReplaceChild("leg1_r1", CubeListBuilder.create().texOffs(40, 42).mirror().addBox(-7.0F, -9.0F, 5.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 9.0F, -7.0F, -0.0436F, 0.0F, 0.0F));

      PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(24, 42).addBox(-1.0F, 0.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, -6.0F));

      PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(24, 42).mirror().addBox(-3.0F, 0.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 15.0F, -6.0F));

      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public ModelPart getHead() {
      return this.head;
   }

   @Override
   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.isCrazy()) {
         float realLimb = limbSwingAmount /* * 1.5F*/;
         this.head.xRot = headPitch * ((float)Math.PI / 180F) + (Mth.cos(limbSwing * 2F) * 1.4F * realLimb);
         this.head.yRot = netHeadYaw * ((float)Math.PI / 180F) + (Mth.cos(limbSwing * 2F) * 1.4F * realLimb);
         this.rightHindLeg.xRot = Mth.cos(limbSwing * 2F) * 1.4F * realLimb;
         this.body.xRot = Mth.cos(limbSwing * 2F) * 1.4F * realLimb;
         this.leftHindLeg.xRot = Mth.cos(limbSwing * 2F + (float)Math.PI) * 1.4F * realLimb;
         this.rightFrontLeg.xRot = Mth.cos(limbSwing * 2F + (float)Math.PI) * 1.4F * realLimb;
         this.leftFrontLeg.xRot = Mth.cos(limbSwing * 2F) * 1.4F * realLimb;

      } else{
         super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         this.body.xRot = 0F;
      }
   }
}