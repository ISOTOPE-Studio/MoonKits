package cc.isotopestudio.MoonKits.projectile;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class ProjectileHitListener implements Listener {

	@EventHandler
	public void onProjectileHit(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player) &&

		event.getEntity() instanceof LivingEntity &&

		event.getCause() == DamageCause.PROJECTILE &&

		event.getDamager() instanceof Projectile)

			if (event.getDamager() instanceof Snowball || event.getDamager() instanceof Egg
					|| event.getDamager() instanceof FishHook) {
				event.setDamage(0);
				event.setCancelled(true);
			}
	}

}
