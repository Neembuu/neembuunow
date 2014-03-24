/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package neembuu.vfs.readmanager.rqm;

/**
 *
 * @author Shashank Tulsyan
 */
public enum ReadRequestSplitType {

    SPECIAL_REQUEST,
    LINEAR_READ__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS,
    LINEAR_READ__ZERO_FILL_READ_REQUEST__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS,
    LINEAR_READ__COMPLETELY_INSIDE_A_DOWNLOADED_REGION,
    SPLITTED_READ__START__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS,
    SPLITTED_READ__START__ZERO_FILL_READ_REQUEST__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS,
    SPLITTED_READ__START_OR_INTERMIDIATE__COMPLETELY_INSIDE,
    LINEAR_READ__CROSSES_AUTHORITY_LIMIT,
    SPLITTED_READ__LAST_SPLIT,
    SPLITTED_READ__START_LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_A_NEW_CONNECTION,
    SPLITTED_READ__START_LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_ZERO_FILL_READ_REQUEST,
    SPLITTED_READ__START_LIES_BETWEEN_TWO_DOWNLOADED_REGIONS,
    LINEAR_READ___LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_A_NEW_CONNECTION,
    LINEAR_READ___LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_ZERO_FILL_READ_REQUEST,
    LINEAR_READ___LIES_BETWEEN_TWO_DOWNLOADED_REGIONS

}
