package core.basesyntax.service.impl;

import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitOperations;
import java.util.Map;

public class SupplyFruitOperation implements FruitOperations {
    private static Map<String, Fruit> storage = Fruit.getFruitStorage();

    @Override
    public void doOperation(FruitDto fruitDto) {
        String fruitName = fruitDto.getFruitName();
        Integer amountToSupply = fruitDto.getAmount();
        Fruit fruit;
        if (storage.containsKey(fruitName)) {
            fruit = getFruit(fruitDto, fruitName, amountToSupply);
            if (fruit == null) {
                return;
            }
        } else {
            fruit = new Fruit(fruitDto.getFruitDtoDate(),
                    amountToSupply);
        }
        storage.put(fruitName, fruit);
    }

    Fruit getFruit(
            FruitDto fruitDto, String key, Integer quantityToAdd) {
        Fruit fruit = storage.get(key);
        while (fruit.getNext() != null) {
            if (fruit.getDate().equals(fruitDto.getFruitDtoDate())) {
                fruit.setAmount(fruit.getAllFruitAmount() + quantityToAdd);
                return null;
            }
            fruit = fruit.getNext();
        }
        fruit.setNext(new Fruit(fruitDto.getFruitDtoDate(), quantityToAdd));
        return fruit;
    }
}
