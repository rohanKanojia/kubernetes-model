/**
 * Copyright (C) 2011 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.kubernetes.internal;

import io.fabric8.kubernetes.api.model.HasMetadata;

import java.util.Comparator;

public class HasMetadataComparator implements Comparator<HasMetadata> {

    private Integer getKindValue(String kind) {
        try {
            switch (kind) {
                case "SecurityContextConstraints":
                    return 0;
                case "Namespace":
                case "Project":
                case "ProjectRequest":
                    return 1;
                case "Secret":
                    return 2;
                case "ServiceAccount":
                    return 3;
                case "OAuthClient":
                    return 4;
                case "Service":
                    return 5;
                case "PolicyBinding":
                    return 6;
                case "ClusterPolicyBinding":
                    return 7;
                case "Role":
                    return 8;
                case "RoleBinding":
                    return 9;
                case "PersistentVolume":
                    return 20;
                case "PersistentVolumeClaim":
                    return 21;
                case "ImageStream":
                    return 30;
                case "ImageStreamTag":
                    return 31;
                default:
                    return 100;
            }
        } catch (IllegalArgumentException e) {
            return 100;
        }
    }

    @Override
    public int compare(HasMetadata a, HasMetadata b) {
        if (a == null || b == null) {
            throw new NullPointerException("Cannot compare null HasMetadata objects");
        }
        if (a == b || a.equals(b)) {
            return 0;
        }

        int kindOrderCompare = getKindValue(a.getKind()).compareTo(getKindValue(b.getKind()));
        if (kindOrderCompare != 0) {
            return kindOrderCompare;
        }

        String classNameA = a.getClass().getSimpleName();
        String classNameB = b.getClass().getSimpleName();

        int classCompare = classNameA.compareTo(classNameB);
        if (classCompare != 0) {
            return classCompare;
        }
        return a.getMetadata().getName().compareTo(b.getMetadata().getName());
    }
}
