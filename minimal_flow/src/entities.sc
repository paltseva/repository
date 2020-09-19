require: dict/cat.csv
  name = cat

require: dict/dog.csv
  name = dog

require: dict/menu.csv
  name = menu


patterns:

  $cat = $entity<cat>

  $dog = $entity<dog>

  $menu = $entity<menu>

