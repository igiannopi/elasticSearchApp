package com.cs.elasticsearch.search.util;

import com.cs.elasticsearch.helper.Mappings;
import com.cs.elasticsearch.search.SearchRequestDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SearchUtil {

	private SearchUtil() {
	}

	public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDTO dto) {
		try {

			final int page = dto.getPage();
			final int size = dto.getSize();
			final int from = page <= 0 ? 0 : page * size;

			SearchSourceBuilder builder = new SearchSourceBuilder()
					// from: means from which document you want to search
					.from(from).size(size).postFilter(getQueryBuilder(dto));

			if (dto.getSortBy() != null) {
				builder = builder.sort(dto.getSortBy(), dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
			}

			final SearchRequest request = new SearchRequest(indexName);
			request.source(builder);

			return request;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Optional<QueryBuilder> createSingleQ(String fieldName, Supplier<String> s) {
		return Optional.ofNullable(s.get()).map(x -> QueryBuilders.matchQuery(fieldName, x));
	}

	public static Optional<QueryBuilder> createSingleDateQ(String fieldName, Supplier<Date> s) {
		return Optional.ofNullable(s.get()).map(x -> QueryBuilders.rangeQuery(fieldName).gte(x));
	}

	public static List<QueryBuilder> createMultiQ(String fieldName, Supplier<List<String>> s) {
		List<QueryBuilder> queryBuilders = new ArrayList<QueryBuilder>();

		Optional.ofNullable(s.get()).orElse(new ArrayList<String>()).forEach(x -> {
			QueryBuilder qb = QueryBuilders.matchQuery(fieldName, x).operator(Operator.OR);
			queryBuilders.add(qb);
		});
		return queryBuilders;

	}

	private static QueryBuilder getQueryBuilder(final SearchRequestDTO dto) {

		if (dto == null) {
			return null;
		}

		final List<QueryBuilder> queryBuilders = new ArrayList<QueryBuilder>();

		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		BoolQueryBuilder queryBuilderForUids = QueryBuilders.boolQuery();

		createMultiQ(Mappings.UID, dto::getUids).forEach(q->queryBuilderForUids.should(q));
		createSingleQ(Mappings.CONVICTING_MEMBER_STATE, dto::getConvictingMemberState).ifPresent(queryBuilders::add);
		createSingleQ(Mappings.OFFICIAL_USERNAME, dto::getOfficialUsername).ifPresent(queryBuilders::add);
		createSingleQ(Mappings.CONTACT_PERSON_EMAIL, dto::getContactPersonEmail).ifPresent(queryBuilders::add);
		createSingleQ(Mappings.MEMBER_STATE_REFERENCE_CODE, dto::getMemberStateReferenceCode).ifPresent(queryBuilders::add);
		createSingleQ(Mappings.CONVICTING_MEMBER_STATE_CENTRAL_AUTHORITY, dto::getConvictingMemberStateCentralAuthority).ifPresent(queryBuilders::add);
		createSingleDateQ(Mappings.CREATION_DATE, dto::getCreationDate).ifPresent(queryBuilders::add);
		createSingleDateQ(Mappings.MODIFICATION_DATE, dto::getModificationDate).ifPresent(queryBuilders::add);
		createSingleQ(Mappings.CONTACT_PERSON_FIRST_NAME, dto::getConvictingMemberState).ifPresent(queryBuilders::add);
		createSingleQ(Mappings.CONTACT_PERSON_FIRST_SURNAME, dto::getContactPersonFirstSurname).ifPresent(queryBuilders::add);
		createSingleQ(Mappings.CONTACT_PERSON_SECOND_SURNAME, dto::getContactPersonSecondSurname).ifPresent(queryBuilders::add);
		createSingleQ(Mappings.CONTACT_PERSON_PHONE, dto::getContactPersonPhone).ifPresent(queryBuilders::add);
		createSingleQ(Mappings.CONTACT_PERSON_FAX, dto::getContactPersonFax).ifPresent(queryBuilders::add);
		queryBuilders.addAll(createMultiQ(Mappings.MOTHER_FIRST_NAMES, dto::getMotherFirstNames));
		queryBuilders.addAll(createMultiQ(Mappings.MOTHER_SURNAMES, dto::getMotherSurnames));
		queryBuilders.addAll(createMultiQ(Mappings.FATHER_FIRSTNAMES, dto::getFatherFirstnames));
		queryBuilders.addAll(createMultiQ(Mappings.FATHER_SURNAMES, dto::getFatherSurnames));

		queryBuilders.forEach(q -> queryBuilder.must(q));
		if (queryBuilderForUids != null) {
			queryBuilder.must(queryBuilderForUids);
		}

		return queryBuilder;
	}
}
