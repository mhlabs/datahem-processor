package org.datahem.processor.measurementprotocol.v2.utils;

/*-
 * ========================LICENSE_START=================================
 * Datahem.processor.measurementprotocol
 * %%
 * Copyright (C) 2018 - 2019 Robert Sahlin
 * %%
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 * =========================LICENSE_END==================================
 */


import org.datahem.protobuf.measurementprotocol.v2.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;


public class PropertyEntity {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyEntity.class);

    public PropertyEntity() {
    }

    public Property build(Map<String, String> pm) {
        try {
            Property.Builder builder = Property.newBuilder();
            Optional.ofNullable(pm.get("ds")).ifPresent(builder::setDataSource);
            Optional.ofNullable(pm.get("gtm")).ifPresent(builder::setGtmContainerId);
            Optional.ofNullable(pm.get("tid")).ifPresent(builder::setTrackingId);
            return builder.build();
        } catch (IllegalArgumentException e) {
            LOG.error(e.toString());
            return null;
        }
    }
}
