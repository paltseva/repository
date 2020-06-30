require: dict/cat.csv
  name = cat

require: dict/dog.csv
  name = dog


patterns:

  $cat = $entity<cat>

  $dog = $entity<dog>

