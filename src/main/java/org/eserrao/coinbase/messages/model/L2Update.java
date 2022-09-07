package org.eserrao.coinbase.messages.model;

import org.eserrao.model.SideType;

public record L2Update(SideType sideType, double price, double size) {
}
