/**
 * Copyright (c) 2002-2014 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.compiler.v2_1.ast.rewriters

import org.neo4j.cypher.internal.commons.CypherFunSuite
import org.neo4j.cypher.internal.compiler.v2_1.planner.AstRewritingTestSupport

class InliningContextCreatorTest extends CypherFunSuite with AstRewritingTestSupport {

  val identA = ident("a")
  val identB = ident("b")

  test("should not spoil aliased node identifiers") {
    val ast = parser.parse("match (a) with a as b match (b) return b")

    val context = inliningContextCreator(ast)

    context.projections should equal(Map(identB -> identA))
    context.alias(identB) should equal(Some(identA))
  }

  test("should not spoil aliased relationship identifiers") {
    val ast = parser.parse("match ()-[a]->() with a as b match ()-[b]->() return b")

    val context = inliningContextCreator(ast)

    context.projections should equal(Map(identB -> identA))
    context.alias(identB) should equal(Some(identA))
  }
}
