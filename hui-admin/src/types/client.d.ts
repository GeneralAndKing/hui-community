export type paths = {
  "/sys-api/sys-user/{sysUserId}": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put: operations["updateSysUser"];
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/sys-api/sys-user/{sysUserId}/password": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put: operations["updatePassword"];
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/sys-api/shop/{shopId}/role": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put: operations["enableRoles"];
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/sys-api/shop-category/{shopCategoryId}": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put: operations["update"];
    post?: never;
    delete: operations["remove_1"];
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
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
  "/sys-api/community/{communityId}/role/{sysUserRoleId}": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put: operations["updateRole"];
    post?: never;
    delete: operations["removeRole"];
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
    delete: operations["cancelRoles"];
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/sys-api/sys-user/{sysUserId}/lock": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["lock"];
    delete: operations["unlock"];
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/sys-api/shop-category": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["add"];
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
  "/sys-api/sys-user/check-username": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["checkUsername"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/sys-api/shop/{shopId}": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["detail"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/sys-api/shop/page": {
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
  "/sys-api/shop-category/page": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["page_1"];
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
  "/sys-api/community/{communityId}/role/page": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["remove"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/sys-api/community/page": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["communityPage"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/shop-category": {
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
  "/area": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["all_3"];
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
          "application/json": {
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
            "application/json": {
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
          "application/json": {
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
            "application/json": {
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
          "application/json": {
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
            "application/json": {
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
    UpdateSysUserRequest: {
      displayName?: string;
      username?: string;
      phone?: string;
      email?: string;
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
      email?: string;
      /** Format: date-time */
      lockedTime?: Date;
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
    UpdateShowRoleRequest: {
      /** @enum {string} */
      role?: UpdateShowRoleRequestRole;
      /** Format: date-time */
      expiredTime?: Date;
    };
    ShopRoleMappingResponse: {
      id?: string;
      createBy?: string;
      updateBy?: string;
      /** Format: date-time */
      updateTime?: Date;
      /** Format: date-time */
      createTime?: Date;
      roleId?: string;
      shopId?: string;
      /** Format: date-time */
      expiredTime?: Date;
    };
    UpdateShopCategoryRequest: {
      name?: string;
      image?: string;
      description?: string;
      parentId?: string;
    };
    ShopCategoryResponse: {
      id?: string;
      createBy?: string;
      updateBy?: string;
      /** Format: date-time */
      updateTime?: Date;
      /** Format: date-time */
      createTime?: Date;
      name?: string;
      image?: string;
      description?: string;
      parentId?: string;
      parentName?: string;
    };
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
    UpdateSysUserUserRoleRequest: {
      permissionIds: string[];
      name: string;
      description: string;
    };
    UpdateRolesRequest: {
      roleIds?: string[];
    };
    AddShopCategoryRequest: {
      name?: string;
      image?: string;
      description?: string;
      parentId?: string;
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
      email?: string;
      /** Format: date-time */
      expiredTime?: Date;
    };
    AddSysUserRoleRequest: {
      permissionIds: string[];
      name: string;
      description: string;
    };
    ShopDetailResponse: {
      id?: string;
      createBy?: string;
      updateBy?: string;
      /** Format: date-time */
      updateTime?: Date;
      /** Format: date-time */
      createTime?: Date;
      name?: string;
      address?: string;
      phone?: string;
      areaId?: string;
      facadeImg?: string;
      businessImg?: string[];
      image?: string;
      notice?: string;
      owner?: components["schemas"]["ShopkeeperResponse"];
      /** Format: float */
      longitude?: number;
      /** Format: float */
      latitude?: number;
      roles?: components["schemas"]["ShopRoleResponse"][];
      categories?: components["schemas"]["ShopCategoryResponse"][];
      communities?: components["schemas"]["CommunityResponse"][];
    };
    ShopRoleResponse: {
      id?: string;
      createBy?: string;
      updateBy?: string;
      /** Format: date-time */
      updateTime?: Date;
      /** Format: date-time */
      createTime?: Date;
      name?: string;
      description?: string;
      permissions?: components["schemas"]["PermissionResponse"][];
    };
    ShopkeeperResponse: {
      id?: string;
      createBy?: string;
      updateBy?: string;
      /** Format: date-time */
      updateTime?: Date;
      /** Format: date-time */
      createTime?: Date;
      name?: string;
      description?: string;
      openId?: string;
    };
    Pageable: {
      /** Format: int32 */
      page?: number;
      /** Format: int32 */
      size?: number;
      sort?: string[];
    };
    PageMetadata: {
      /** Format: int64 */
      size?: number;
      /** Format: int64 */
      number?: number;
      /** Format: int64 */
      totalElements?: number;
      /** Format: int64 */
      totalPages?: number;
    };
    PagedModelShopSysShowResponse: {
      content?: components["schemas"]["ShopSysShowResponse"][];
      page?: components["schemas"]["PageMetadata"];
    };
    ShopSysShowResponse: {
      id?: string;
      createBy?: string;
      updateBy?: string;
      /** Format: date-time */
      updateTime?: Date;
      /** Format: date-time */
      createTime?: Date;
      name?: string;
      address?: string;
      phone?: string;
      areaId?: string;
      facadeImg?: string;
      image?: string;
      notice?: string;
      /** Format: float */
      longitude?: number;
      /** Format: float */
      latitude?: number;
      categories?: components["schemas"]["ShopCategoryResponse"][];
    };
    PagedModelShopCategoryResponse: {
      content?: components["schemas"]["ShopCategoryResponse"][];
      page?: components["schemas"]["PageMetadata"];
    };
    PaymentCategoryResponse: {
      id?: string;
      name?: string;
      icon?: string;
    };
    CommunitySysUserResponse: {
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
      email?: string;
      /** Format: date-time */
      lockedTime?: Date;
      /** Format: date-time */
      expiredTime?: Date;
      roles?: string[];
    };
    PagedModelCommunitySysUserResponse: {
      content?: components["schemas"]["CommunitySysUserResponse"][];
      page?: components["schemas"]["PageMetadata"];
    };
    PagedModelSysUserRolePageResponse: {
      content?: components["schemas"]["SysUserRolePageResponse"][];
      page?: components["schemas"]["PageMetadata"];
    };
    SysUserRolePageResponse: {
      id?: string;
      createBy?: string;
      updateBy?: string;
      /** Format: date-time */
      updateTime?: Date;
      /** Format: date-time */
      createTime?: Date;
      name?: string;
      description?: string;
      permissions?: components["schemas"]["PermissionResponse"][];
    };
    PagedModelCommunityResponse: {
      content?: components["schemas"]["CommunityResponse"][];
      page?: components["schemas"]["PageMetadata"];
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
  updateSysUser: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        sysUserId: string;
      };
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["UpdateSysUserRequest"];
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
  updatePassword: {
    parameters: {
      query: {
        newPassword: string;
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
        content?: never;
      };
    };
  };
  enableRoles: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        shopId: string;
      };
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["UpdateShowRoleRequest"];
      };
    };
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["ShopRoleMappingResponse"];
        };
      };
    };
  };
  update: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        shopCategoryId: string;
      };
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["UpdateShopCategoryRequest"];
      };
    };
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["ShopCategoryResponse"];
        };
      };
    };
  };
  remove_1: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        shopCategoryId: string;
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
        content?: never;
      };
    };
  };
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
  updateRole: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        communityId: string;
        sysUserRoleId: string;
      };
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["UpdateSysUserUserRoleRequest"];
      };
    };
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
  removeRole: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        communityId: string;
        sysUserRoleId: string;
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
          "*/*": components["schemas"]["CommunitySysUserResponse"][];
        };
      };
    };
  };
  assignRoles: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        sysUserId: string;
      };
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["UpdateRolesRequest"];
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
  cancelRoles: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        sysUserId: string;
      };
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["UpdateRolesRequest"];
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
  lock: {
    parameters: {
      query?: never;
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
  unlock: {
    parameters: {
      query?: never;
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
  add: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["AddShopCategoryRequest"];
      };
    };
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["ShopCategoryResponse"];
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
      query?: never;
      header?: never;
      path: {
        communityId: string;
      };
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["AddSysUserRoleRequest"];
      };
    };
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
  checkUsername: {
    parameters: {
      query: {
        username: string;
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
          "*/*": boolean;
        };
      };
    };
  };
  detail: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        shopId: string;
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
          "*/*": components["schemas"]["ShopDetailResponse"];
        };
      };
    };
  };
  page: {
    parameters: {
      query: {
        pageable: components["schemas"]["Pageable"];
        communityId?: string;
        categoryIds?: string[];
        likedName?: string;
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
          "*/*": components["schemas"]["PagedModelShopSysShowResponse"];
        };
      };
    };
  };
  page_1: {
    parameters: {
      query: {
        parentId?: string;
        likedName?: string;
        pageable: components["schemas"]["Pageable"];
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
          "*/*": components["schemas"]["PagedModelShopCategoryResponse"];
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
          "*/*": components["schemas"]["PagedModelCommunitySysUserResponse"];
        };
      };
    };
  };
  remove: {
    parameters: {
      query: {
        permissionIds?: string[];
        likedName?: string;
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
          "*/*": components["schemas"]["PagedModelSysUserRolePageResponse"];
        };
      };
    };
  };
  communityPage: {
    parameters: {
      query: {
        areaOrParentAreaId?: string;
        likedName?: string;
        likedCode?: string;
        pageable: components["schemas"]["Pageable"];
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
          "*/*": components["schemas"]["PagedModelCommunityResponse"];
        };
      };
    };
  };
  all_2: {
    parameters: {
      query?: {
        parentId?: string;
        all?: boolean;
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
          "*/*": components["schemas"]["ShopCategoryResponse"][];
        };
      };
    };
  };
  all_3: {
    parameters: {
      query?: {
        parentId?: string;
        all?: boolean;
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
          "*/*": components["schemas"]["AreaResponse"][];
        };
      };
    };
  };
}
export enum UpdateShowRoleRequestRole {
  DEPOSIT = "DEPOSIT",
  TIME = "TIME",
  SCORE = "SCORE",
  DISPATCH = "DISPATCH"
}
