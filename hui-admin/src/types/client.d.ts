export type paths = {
    "/sys-api/community/{communityId}": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get?: never;
        put: operations["updateCommunityById"];
        post?: never;
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/sys-api/sys-user/{sysUserId}/roles": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get?: never;
        put?: never;
        post: operations["assignRoles"];
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/sys-api/payment": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get?: never;
        put?: never;
        post: operations["createPayment"];
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/sys-api/community": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get?: never;
        put?: never;
        post: operations["addCommunity"];
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/sys-api/community/{communityId}/sys-user": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get?: never;
        put?: never;
        post: operations["addSysUser"];
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/sys-api/community/{communityId}/role": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get?: never;
        put?: never;
        post: operations["addNewSysUserRole"];
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/user-api/user/my-cards": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get: operations["myCards"];
        put?: never;
        post?: never;
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/user-api/shop": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get: operations["page"];
        put?: never;
        post?: never;
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/sys-api/sys-user/my": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get: operations["my"];
        put?: never;
        post?: never;
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/sys-api/permission": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get: operations["all"];
        put?: never;
        post?: never;
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/sys-api/payment-category": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get: operations["all_1"];
        put?: never;
        post?: never;
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/sys-api/community/{communityId}/sys-user/page": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get: operations["sysUserPage"];
        put?: never;
        post?: never;
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/area": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get: operations["all_2"];
        put?: never;
        post?: never;
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/sys-api/login": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get?: never;
        put?: never;
        post: {
            parameters: {
                query?: never;
                header?: never;
                path?: never;
                cookie?: never;
            };
            requestBody?: {
                content: {
                    "*/*": {
                        username?: string;
                        password?: string;
                    };
                };
            };
            responses: {
                200: {
                    headers: {
                        [name: string]: unknown;
                    };
                    content: {
                        "*/*": {
                            id?: string;
                            subject?: string;
                            username?: string;
                            accessToken?: string;
                            refreshToken?: string;
                        };
                    };
                };
            };
        };
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/user-api/login": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get?: never;
        put?: never;
        post: {
            parameters: {
                query?: never;
                header?: never;
                path?: never;
                cookie?: never;
            };
            requestBody?: {
                content: {
                    "*/*": {
                        code?: string;
                        username?: string;
                    };
                };
            };
            responses: {
                200: {
                    headers: {
                        [name: string]: unknown;
                    };
                    content: {
                        "*/*": {
                            id?: string;
                            subject?: string;
                            username?: string;
                            accessToken?: string;
                            refreshToken?: string;
                        };
                    };
                };
            };
        };
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
    "/refresh-token": {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        get?: never;
        put?: never;
        post: {
            parameters: {
                query?: never;
                header?: never;
                path?: never;
                cookie?: never;
            };
            requestBody?: {
                content: {
                    "*/*": {
                        refreshToken?: string;
                        id?: string;
                    };
                };
            };
            responses: {
                200: {
                    headers: {
                        [name: string]: unknown;
                    };
                    content: {
                        "*/*": {
                            id?: string;
                            subject?: string;
                            username?: string;
                            accessToken?: string;
                            refreshToken?: string;
                        };
                    };
                };
            };
        };
        delete?: never;
        options?: never;
        head?: never;
        patch?: never;
        trace?: never;
    };
};
export type webhooks = Record<string, never>;
export type components = {
    schemas: {
        UpdateCommunityRequest: {
            code?: string;
            name?: string;
            address?: string;
            areaId?: string;
            /** Format: float */
            longitude?: number;
            /** Format: float */
            latitude?: number;
        };
        CommunityResponse: {
            id?: string;
            createBy?: string;
            updateBy?: string;
            /** Format: date-time */
            updateTime?: Date;
            /** Format: date-time */
            createTime?: Date;
            code?: string;
            name?: string;
            address?: string;
            areaId?: string;
            /** Format: float */
            longitude?: number;
            /** Format: float */
            latitude?: number;
        };
        AssignRolesRequest: {
            roleIds?: string[];
        };
        PermissionResponse: {
            id?: string;
            createBy?: string;
            updateBy?: string;
            /** Format: date-time */
            updateTime?: Date;
            /** Format: date-time */
            createTime?: Date;
            name?: string;
            type?: string;
            description?: string;
        };
        SysUserResponse: {
            id?: string;
            createBy?: string;
            updateBy?: string;
            /** Format: date-time */
            updateTime?: Date;
            /** Format: date-time */
            createTime?: Date;
            displayName?: string;
            username?: string;
            phone?: string;
            /** Format: date-time */
            lockedTime?: Date;
            /** Format: date-time */
            expiredTime?: Date;
            roles?: components["schemas"]["SysUserRoleResponse"][];
        };
        SysUserRoleResponse: {
            id?: string;
            createBy?: string;
            updateBy?: string;
            /** Format: date-time */
            updateTime?: Date;
            /** Format: date-time */
            createTime?: Date;
            communityName?: string;
            communityId?: string;
            name?: string;
            description?: string;
            permissions?: components["schemas"]["PermissionResponse"][];
        };
        AddCommunityRequest: {
            code?: string;
            name?: string;
            address?: string;
            areaId?: string;
            /** Format: float */
            longitude?: number;
            /** Format: float */
            latitude?: number;
        };
        AddSysUserRequest: {
            displayName?: string;
            username?: string;
            password?: string;
            phone?: string;
            /** Format: date-time */
            expiredTime?: Date;
        };
        AddSysRoleRequest: {
            permissionIds?: string[];
            name?: string;
            description?: string;
        };
        Pageable: {
            /** Format: int32 */
            page?: number;
            /** Format: int32 */
            size?: number;
            sort?: string[];
        };
        PageShopShowResponse: {
            /** Format: int64 */
            totalElements?: number;
            /** Format: int32 */
            totalPages?: number;
            /** Format: int32 */
            size?: number;
            content?: components["schemas"]["ShopShowResponse"][];
            /** Format: int32 */
            number?: number;
            sort?: components["schemas"]["SortObject"][];
            /** Format: int32 */
            numberOfElements?: number;
            pageable?: components["schemas"]["PageableObject"];
            last?: boolean;
            first?: boolean;
            empty?: boolean;
        };
        PageableObject: {
            /** Format: int64 */
            offset?: number;
            sort?: components["schemas"]["SortObject"][];
            paged?: boolean;
            /** Format: int32 */
            pageNumber?: number;
            /** Format: int32 */
            pageSize?: number;
            unpaged?: boolean;
        };
        ShopShowResponse: Record<string, never>;
        SortObject: {
            direction?: string;
            nullHandling?: string;
            ascending?: boolean;
            property?: string;
            ignoreCase?: boolean;
        };
        PaymentCategoryResponse: {
            id?: string;
            name?: string;
            icon?: string;
        };
        PageSysUserPageResponse: {
            /** Format: int64 */
            totalElements?: number;
            /** Format: int32 */
            totalPages?: number;
            /** Format: int32 */
            size?: number;
            content?: components["schemas"]["SysUserPageResponse"][];
            /** Format: int32 */
            number?: number;
            sort?: components["schemas"]["SortObject"][];
            /** Format: int32 */
            numberOfElements?: number;
            pageable?: components["schemas"]["PageableObject"];
            last?: boolean;
            first?: boolean;
            empty?: boolean;
        };
        SysUserPageResponse: {
            id?: string;
            createBy?: string;
            updateBy?: string;
            /** Format: date-time */
            updateTime?: Date;
            /** Format: date-time */
            createTime?: Date;
            displayName?: string;
            username?: string;
            phone?: string;
            /** Format: date-time */
            lockedTime?: Date;
            /** Format: date-time */
            expiredTime?: Date;
            roles?: string[];
        };
        AreaResponse: {
            id?: string;
            parentId?: string;
            code?: string;
            /** Format: int32 */
            level?: number;
            name?: string;
            pinyin?: string;
            pinyinPrefix?: string;
        };
    };
    responses: never;
    parameters: never;
    requestBodies: never;
    headers: never;
    pathItems: never;
};
export type $defs = Record<string, never>;
export interface operations {
    updateCommunityById: {
        parameters: {
            query?: never;
            header?: never;
            path: {
                communityId: string;
            };
            cookie?: never;
        };
        requestBody: {
            content: {
                "application/json": components["schemas"]["UpdateCommunityRequest"];
            };
        };
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": components["schemas"]["CommunityResponse"];
                };
            };
        };
    };
    assignRoles: {
        parameters: {
            query: {
                request: components["schemas"]["AssignRolesRequest"];
            };
            header?: never;
            path: {
                sysUserId: string;
            };
            cookie?: never;
        };
        requestBody?: never;
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": components["schemas"]["SysUserResponse"];
                };
            };
        };
    };
    createPayment: {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        requestBody?: never;
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content?: never;
            };
        };
    };
    addCommunity: {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        requestBody: {
            content: {
                "application/json": components["schemas"]["AddCommunityRequest"];
            };
        };
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": components["schemas"]["CommunityResponse"];
                };
            };
        };
    };
    addSysUser: {
        parameters: {
            query?: never;
            header?: never;
            path: {
                communityId: string;
            };
            cookie?: never;
        };
        requestBody: {
            content: {
                "application/json": components["schemas"]["AddSysUserRequest"];
            };
        };
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": components["schemas"]["SysUserResponse"];
                };
            };
        };
    };
    addNewSysUserRole: {
        parameters: {
            query: {
                request: components["schemas"]["AddSysRoleRequest"];
            };
            header?: never;
            path: {
                communityId: string;
            };
            cookie?: never;
        };
        requestBody?: never;
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": components["schemas"]["SysUserRoleResponse"];
                };
            };
        };
    };
    myCards: {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        requestBody?: never;
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": string;
                };
            };
        };
    };
    page: {
        parameters: {
            query: {
                pageable: components["schemas"]["Pageable"];
                communityId?: string;
                categoryId?: string;
                longitude?: number;
                latitude?: number;
            };
            header?: never;
            path?: never;
            cookie?: never;
        };
        requestBody?: never;
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": components["schemas"]["PageShopShowResponse"];
                };
            };
        };
    };
    my: {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        requestBody?: never;
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": components["schemas"]["SysUserResponse"];
                };
            };
        };
    };
    all: {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        requestBody?: never;
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": components["schemas"]["PermissionResponse"][];
                };
            };
        };
    };
    all_1: {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        requestBody?: never;
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": components["schemas"]["PaymentCategoryResponse"][];
                };
            };
        };
    };
    sysUserPage: {
        parameters: {
            query: {
                likedUsername?: string;
                likedDisplayName?: string;
                pageable: components["schemas"]["Pageable"];
            };
            header?: never;
            path: {
                communityId: string;
            };
            cookie?: never;
        };
        requestBody?: never;
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": components["schemas"]["PageSysUserPageResponse"];
                };
            };
        };
    };
    all_2: {
        parameters: {
            query?: never;
            header?: never;
            path?: never;
            cookie?: never;
        };
        requestBody?: never;
        responses: {
            /** @description OK */
            200: {
                headers: {
                    [name: string]: unknown;
                };
                content: {
                    "*/*": components["schemas"]["AreaResponse"][];
                };
            };
        };
    };
}
