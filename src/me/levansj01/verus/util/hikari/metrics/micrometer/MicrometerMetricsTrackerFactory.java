package me.levansj01.verus.util.hikari.metrics.micrometer;

import me.levansj01.verus.util.hikari.metrics.IMetricsTracker;
import me.levansj01.verus.util.hikari.metrics.MetricsTrackerFactory;
import me.levansj01.verus.util.hikari.metrics.PoolStats;
import io.micrometer.core.instrument.MeterRegistry;

public class MicrometerMetricsTrackerFactory implements MetricsTrackerFactory
{

   private final MeterRegistry registry;

   public MicrometerMetricsTrackerFactory(MeterRegistry registry)
   {
      this.registry = registry;
   }

   @Override
   public IMetricsTracker create(String poolName, PoolStats poolStats)
   {
      return new MicrometerMetricsTracker(poolName, poolStats, registry);
   }
}
