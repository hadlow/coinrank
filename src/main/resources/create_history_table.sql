CREATE TABLE `history_{coin_id}` (
  `id` int(11) UNSIGNED NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `price_usd` int(10) UNSIGNED NOT NULL,
  `price_btc` int(10) UNSIGNED NOT NULL,
  `market_cap` bigint(20) UNSIGNED NOT NULL,
  `volume_usd` bigint(20) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `history_{coin_id}` ADD PRIMARY KEY (`id`);

ALTER TABLE `history_{coin_id}` MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;