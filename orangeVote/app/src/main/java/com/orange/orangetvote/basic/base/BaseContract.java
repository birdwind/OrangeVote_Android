package com.orange.orangetvote.basic.base;

public interface BaseContract<E extends BaseEntity> {

    interface ContractPresent extends Present {
        void contractPresent();
    }

    interface ContractView<E> extends BaseView {
        void viewSuccess(E entity);
        void viewError(String reason);
    }
}
