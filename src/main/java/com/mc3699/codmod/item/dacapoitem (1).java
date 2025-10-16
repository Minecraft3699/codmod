
package dacapomod.classes;

import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DaCapoItem extends SwordItem {


	private static void OnRightClick(LevelAccessor world, double x, double y, double z, Entity entity,
			ItemStack itemstack) {
		if (entity == null)
			return;
		double particleRadius = 0;
		double particleAmount = 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z),
						BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("dacapomod:orchwork")),
						SoundSource.NEUTRAL, (float) 0.4, 1);
			} else {
				_level.playLocalSound(x, y, z,
						BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("dacapomod:orchwork")),
						SoundSource.NEUTRAL, (float) 0.4, 1, false);
			}
		}
		for (Entity entityiterator : world.getEntities(entity,
				new AABB((x + 14), (y + 14), (z + 14), (x - 14), (y - 14), (z - 14)))) {
			if (entityiterator instanceof LivingEntity) {
				if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(InsanityMobEffect.INSANITY, 300, 0));
			}
		}
		particleAmount = 200;
		particleRadius = 14;
		for (int index0 = 0; index0 < (int) particleAmount; index0++) {
			world.addParticle(ParticleTypes.NOTE,
					(x + 0 + Mth.nextDouble(RandomSource.create(), -1, 1) * particleRadius),
					(y + 0 + Mth.nextDouble(RandomSource.create(), -1, 1) * particleRadius),
					(z + 0 + Mth.nextDouble(RandomSource.create(), -1, 1) * particleRadius),
					(Mth.nextDouble(RandomSource.create(), -0.05, 0.05)),
					(Mth.nextDouble(RandomSource.create(), -0.05, 0.05)),
					(Mth.nextDouble(RandomSource.create(), -0.05, 0.05)));
		}
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(itemstack.getItem(), 650);
	}
	private static void LivingEntityHitProcedure(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double particleRadius = 0;
		double particleAmount = 0;
		if (0 < (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z),
							BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("dacapomod:capohit")),
							SoundSource.PLAYERS, (float) 0.2, Mth.nextInt(RandomSource.create(), (int) 0.8, (int) 1.2));
				} else {
					_level.playLocalSound(x, y, z,
							BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("dacapomod:capohit")),
							SoundSource.PLAYERS, (float) 0.2, Mth.nextInt(RandomSource.create(), (int) 0.8, (int) 1.2),
							false);
				}
			}
		} else {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z),
							BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("dacapomod:capokill")),
							SoundSource.PLAYERS, (float) 0.2, Mth.nextInt(RandomSource.create(), (int) 0.8, (int) 1.2));
				} else {
					_level.playLocalSound(x, y, z,
							BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("dacapomod:capokill")),
							SoundSource.PLAYERS, (float) 0.2, Mth.nextInt(RandomSource.create(), (int) 0.8, (int) 1.2),
							false);
				}
			}
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth()
				: -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.4) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(InsanityMobEffect.INSANITY, 160, 0));
		}
	}
	private static void EntitySwingProcedure(LevelAccessor world, double x, double y, double z) {
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z),
						BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("dacapomod:caposwing")),
						SoundSource.PLAYERS, (float) 0.4, Mth.nextInt(RandomSource.create(), (int) 0.8, (int) 1.2));
			} else {
				_level.playLocalSound(x, y, z,
						BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("dacapomod:caposwing")),
						SoundSource.PLAYERS, (float) 0.4, Mth.nextInt(RandomSource.create(), (int) 0.8, (int) 1.2),
						false);
			}
		}
	}
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 0f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 0;
		}

		@Override
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_WOODEN_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 24;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of();
		}
	};

	public DaCapoItem() {
		super(TOOL_TIER, new Item.Properties().attributes(SwordItem.createAttributes(TOOL_TIER, 5f, -1.5f)));
	}

	@SubscribeEvent
	public static void handleToolDamage(ModifyDefaultComponentsEvent event) {
		event.modify(DA_CAPO.get(), builder -> builder.remove(DataComponents.MAX_DAMAGE));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		LivingEntityHitProcedure(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return retval;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		OnRightClick(world, entity.getX(), entity.getY(), entity.getZ(), entity, ar.getObject());
		return ar;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list,
			TooltipFlag flag) {
		super.appendHoverText(itemstack, context, list, flag);
		list.add(Component.translatable("item.dacapomod.da_capo.description_0"));
		list.add(Component.translatable("item.dacapomod.da_capo.description_1"));
	}

	@Override
	public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity, InteractionHand hand) {
		boolean retval = super.onEntitySwing(itemstack, entity, hand);
		EntitySwingProcedure(entity.level(), entity.getX(), entity.getY(), entity.getZ());
		return retval;
	}

    public static final DeferredRegister.Items ITEMREGISTRY = DeferredRegister.createItems(DacapomodMod.MODID);
    public static final DeferredItem<Item> DA_CAPO = ITEMREGISTRY.register("da_capo", DaCapoItem::new);

    public static final DeferredRegister<SoundEvent> SOUNDREGISTRY = DeferredRegister.create(Registries.SOUND_EVENT,
            DacapomodMod.MODID);
    public static final DeferredHolder<SoundEvent, SoundEvent> CAPOSWING = SOUNDREGISTRY.register("caposwing",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("dacapomod", "caposwing")));
    public static final DeferredHolder<SoundEvent, SoundEvent> CAPOHIT = SOUNDREGISTRY.register("capohit",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("dacapomod", "capohit")));
    public static final DeferredHolder<SoundEvent, SoundEvent> CAPOKILL = SOUNDREGISTRY.register("capokill",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("dacapomod", "capokill")));
    public static final DeferredHolder<SoundEvent, SoundEvent> INSANE = SOUNDREGISTRY.register("insane",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("dacapomod", "insane")));
    public static final DeferredHolder<SoundEvent, SoundEvent> PLINKYPOO = SOUNDREGISTRY.register("plinkypoo",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("dacapomod", "plinkypoo")));
    public static final DeferredHolder<SoundEvent, SoundEvent> ORCHWORK = SOUNDREGISTRY.register("orchwork",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("dacapomod", "orchwork")));
    public static final DeferredHolder<SoundEvent, SoundEvent> FWDHLY = SOUNDREGISTRY.register("fwdhly",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("dacapomod", "fwdhly")));
    public static final DeferredHolder<SoundEvent, SoundEvent> ZOJNKS = SOUNDREGISTRY.register("zojnks",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("dacapomod", "zojnks")));

    public static final DeferredRegister<CreativeModeTab> CREATIVEMODEREGISTRY = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, DacapomodMod.MODID);

    @SubscribeEvent
    public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
        if (tabData.getTabKey() == CreativeModeTabs.COMBAT) {
            tabData.accept(DA_CAPO.get());
        }
    }
}