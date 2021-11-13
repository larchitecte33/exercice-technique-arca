export class AggregateDto {
  private readonly country: {name:""};
  private readonly aggregateValue: number;

  constructor(country: {name:""}, aggregateValue: number) {
    this.country = country;
    this.aggregateValue = aggregateValue;
  }

}
